package com.ngocduc.projectspringboot.mapper;

import com.ngocduc.projectspringboot.model.dto.request.UserRequest;
import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.model.entity.ERoles;
import com.ngocduc.projectspringboot.model.entity.Roles;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class MapperRegister implements MapperGeneric<Users, UserRequest, UserResponse> {
    @Autowired
    private RolesService rolesService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users mapperRequestToEntity(UserRequest userRequest) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //Xử lý quyền user khi đăng ký
        Set<String> strRoles = userRequest.getListRoles();
        Set<Roles> listRoles = new HashSet<>();
        if (strRoles == null) {
            //User quyen mac dinh Role_user
            Roles userRole = rolesService.findByName(ERoles.ROLE_USER).orElseThrow(() ->
                    new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = rolesService.findByName(ERoles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(adminRole);
                        break;
                    case "user":
                        Roles userRole = rolesService.findByName(ERoles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        listRoles.add(userRole);
                        break;
                }
            });
        }
        return Users.builder().userName(userRequest.getUserName())
                //Mã hóa mật khẩu người dùng khi đăng ký
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .userName(userRequest.getUserName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .fullName(userRequest.getFullName())
                .address(userRequest.getAddress())
                .create_at(new Date())
                .status(true)
                .listRoles(listRoles).build();
    }

    @Override
    public UserResponse mapperEntityToResponse(Users users) {
        return UserResponse.builder().id(users.getId())
                .userName(users.getUserName())
                .password(users.getPassword())
                .email(users.getEmail())
                .fullName(users.getFullName())
                .phone(users.getPhone())
                .address(users.getAddress())
                .listRoles(users.getListRoles()).build();
    }
}
