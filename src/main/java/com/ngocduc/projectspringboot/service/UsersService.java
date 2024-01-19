package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.dto.request.ChangePasswordRequest;
import com.ngocduc.projectspringboot.model.dto.request.LoginRequest;
import com.ngocduc.projectspringboot.model.dto.request.UpdateUser;
import com.ngocduc.projectspringboot.model.dto.request.UserRequest;
import com.ngocduc.projectspringboot.model.dto.response.LoginResponse;
import com.ngocduc.projectspringboot.model.dto.response.OrderResponse;
import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UsersService {
    Page<UserResponse> findAll(Pageable pageable);


    Users findByUserNameAndStatus(String userName, boolean status);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    UserResponse saveOrUpdate(UserRequest userRequest);
    UserResponse addRole(Users users);

    LoginResponse login(LoginRequest loginRequest);

    boolean blockOpenByUserId(long id);
    boolean findUserId(long id);


    Users addRoleForUser(long id);
    List<UserResponse> searchUserByName(String name);

    UserResponse getInfoUser(long userId);
    UserResponse updateInfoUser(long userId, UpdateUser updateUser);

    void changePassword(long user, ChangePasswordRequest req);
    List<OrderResponse> getListHistoryOrder(long userId);
    List<OrderResponse> findAllByUsersAndAndOrder_status(long userId,String status);
    boolean cancel(long userId,long orderId);
}
