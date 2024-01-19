package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.exception.BadRequestException;
import com.ngocduc.projectspringboot.mapper.MapperOrder;
import com.ngocduc.projectspringboot.model.dto.request.ChangePasswordRequest;
import com.ngocduc.projectspringboot.model.dto.request.LoginRequest;
import com.ngocduc.projectspringboot.model.dto.request.UpdateUser;
import com.ngocduc.projectspringboot.model.dto.request.UserRequest;
import com.ngocduc.projectspringboot.model.dto.response.LoginResponse;
import com.ngocduc.projectspringboot.model.dto.response.OrderResponse;
import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.jwt.JwtTokenProvider;
import com.ngocduc.projectspringboot.mapper.MapperRegister;
import com.ngocduc.projectspringboot.model.entity.EOrders;
import com.ngocduc.projectspringboot.model.entity.Orders;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.repository.OrderRepository;
import com.ngocduc.projectspringboot.repository.UsersRepository;
import com.ngocduc.projectspringboot.security.CustomUserDetail;
import com.ngocduc.projectspringboot.service.UsersService;
import com.ngocduc.projectspringboot.token.Token;
import com.ngocduc.projectspringboot.token.TokenRepository;
import com.ngocduc.projectspringboot.token.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UsersServiceImp implements UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MapperRegister mapperRegister;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MapperOrder mapperOrder;


    @Override
    public Page<UserResponse> findAll(Pageable pageable) {
        Page<Users> usersPage = usersRepository.findAll(pageable);

        List<Users> usersList = usersPage.getContent();

        List<UserResponse> userResponseList = usersList.stream()
                .map(products -> mapperRegister.mapperEntityToResponse(products))
                .collect(Collectors.toList());

        var totalUsers = usersPage.getTotalElements();

        return new PageImpl<>(userResponseList, pageable, totalUsers);
    }

    @Override
    public Users findByUserNameAndStatus(String userName, boolean status) {
        return usersRepository.findByUserNameAndStatus(userName, status);
    }

    @Override
    public boolean existsByUserName(String userName) {
        return usersRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    @Override
    public UserResponse saveOrUpdate(UserRequest userRequest) {
        return mapperRegister.mapperEntityToResponse(usersRepository.save(mapperRegister
                .mapperRequestToEntity(userRequest)));
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        //Sinh JWT tra ve client
        String jwt = jwtTokenProvider.generateToken(customUserDetail);
        Users user = usersRepository.findByUserName(loginRequest.getUserName());
        revokeAllUserTokens(user);
        saveUserToken(user, jwt);
        //Lay cac quyen cua user
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());


        return new LoginResponse(jwt, "Bearer", customUserDetail.getUsername(),
                customUserDetail.getEmail(), customUserDetail.getPhone(), listRoles);
    }


    private void saveUserToken(Users user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Users user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    /**
     * @param id
     * @return if account logging false else true
     */
    @Override
    public boolean blockOpenByUserId(long id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        List<Token> tokenOptional = tokenRepository.findAllValidTokenByUser(id);
        long sum = tokenOptional.stream()
                .filter(token -> !token.isExpired() && !token.isRevoked())
                .count();
        if (usersOptional.isPresent() && sum == 0) {
            Users users = usersOptional.get();
            users.setStatus(!users.isStatus());
            usersRepository.save(users);
            return true;
        }
        return false;
    }

    @Override
    public Users addRoleForUser(long id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        if (usersOptional.isPresent()) {
            Users users = usersOptional.get();
            return users;
        }
        return null;
    }
    @Override
    public boolean findUserId(long id) {
        Optional<Users> usersOptional = usersRepository.findById(id);
        return usersOptional.isPresent();
    }
    @Override
    public UserResponse addRole(Users users) {
        return mapperRegister.mapperEntityToResponse(usersRepository.save(users));
    }
    @Override
    public List<UserResponse> searchUserByName(String name) {
        List<UserResponse> userResponseList = new ArrayList<>();
        List<Users> usersList = usersRepository.searchUserByName(name);

        if (usersList != null && !usersList.isEmpty()) {
            userResponseList = usersList.stream()
                    .map(users -> mapperRegister.mapperEntityToResponse(users))
                    .collect(Collectors.toList());
        }

        return userResponseList;
    }

    @Override
    public UserResponse getInfoUser(long userId) {
        return mapperRegister.mapperEntityToResponse(usersRepository.findById(userId).get());
    }

    @Override
    public UserResponse updateInfoUser(long userId, UpdateUser updateUser) {
        Users users = usersRepository.findById(userId).get();
        users.setFullName(updateUser.getFullName());
        users.setPhone(updateUser.getPhone());
        users.setAddress(updateUser.getAddress());
        users.setUpdated_at(new Date());
        usersRepository.save(users);
        return mapperRegister.mapperEntityToResponse(usersRepository.save(users));
    }
    @Override
    public void changePassword(long userId, ChangePasswordRequest req) {
        Users users = usersRepository.findById(userId).get();
        // Validate password
        if (!BCrypt.checkpw(req.getOldPassword(), users.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }
        // Check if newPassword and confirmNewPassword match
        if (!req.getNewPassword().equals(req.getConfirmNewPassword())) {
            throw new BadRequestException("Mật khẩu mới và xác nhận mật khẩu mới không giống nhau");
        }

        String hash = BCrypt.hashpw(req.getNewPassword(), BCrypt.gensalt(12));
        users.setPassword(hash);
        usersRepository.save(users);
    }

    @Override
    public List<OrderResponse> getListHistoryOrder(long userId) {
        Users users = usersRepository.findById(userId).get();

        List<Orders> ordersList = orderRepository.findAllByUsers(users);
        List<OrderResponse> orderResponseList = ordersList.stream()
                .map(orders -> mapperOrder.mapperEntityToResponse(orders))
                .collect(Collectors.toList());
        return orderResponseList;
    }
    @Transactional
    @Override
    public List<OrderResponse> findAllByUsersAndAndOrder_status(long userId,String status) {
        Users users = usersRepository.findById(userId).get();
        List<Orders> ordersList = new ArrayList<>();
        if (status.equals("WAITING")) {
            ordersList = orderRepository.findByOrder_status(EOrders.WAITING);
        }
        if (status.equals("CONFIRM")) {
            ordersList = orderRepository.findByOrder_status(EOrders.CONFIRM);
        }
        if (status.equals("DELIVERY")) {
            ordersList = orderRepository.findByOrder_status(EOrders.DELIVERY);
        }
        if (status.equals("SUCCESS")) {
            ordersList = orderRepository.findByOrder_status(EOrders.SUCCESS);
        }
        if (status.equals("CANCEL")) {
            ordersList = orderRepository.findByOrder_status(EOrders.CANCEL);
        }
        if (status.equals("DENIED")) {
            ordersList = orderRepository.findByOrder_status(EOrders.DENIED);
        }
        List<OrderResponse> orderResponseList = ordersList.stream()
                .filter(orders -> orders.getUsers().equals(users))
                .map(orders -> mapperOrder.mapperEntityToResponse(orders))
                .collect(Collectors.toList());
        return orderResponseList;
    }
    @Override
    public boolean cancel(long userId, long orderId){
        Users users = usersRepository.findById(userId).get();
        Orders orders = orderRepository.findByUsersAndOrder_id(userId,orderId);
        if (orders.getOrder_status().equals(EOrders.WAITING)){
            orders.setOrder_status(EOrders.CANCEL);
            orderRepository.save(orders);
            return true;
        }
        return false;
    }
}
