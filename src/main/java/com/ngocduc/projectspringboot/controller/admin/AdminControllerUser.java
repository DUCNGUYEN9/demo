package com.ngocduc.projectspringboot.controller.admin;


import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.model.entity.ERoles;
import com.ngocduc.projectspringboot.model.entity.Roles;
import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.service.RolesService;
import com.ngocduc.projectspringboot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;
@RestController
public class AdminControllerUser {

    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;


    @GetMapping("/api/v1/admin/users")
    public ResponseEntity<Map<String, Object>> getAllUsersPagSort(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Pageable pageable;
        if ("asc".equals(direction)) {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "email"));
        } else {
            pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "email"));
        }
        Page<UserResponse> pageUser = usersService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();

        data.put("users", pageUser.getContent());
        data.put("totalUsers", pageUser.getTotalElements());
        data.put("totalPage", pageUser.getTotalPages());
        return ResponseEntity.ok(data);
    }

    //add role
    @PostMapping("/api/v1/admin/users/{userId}/role")
    public ResponseEntity<?> addRoleForUsers(@PathVariable long userId) {
        boolean result = usersService.findUserId(userId);
        Set<Roles> listRoles = new HashSet<>();
        if (result ) {
            Roles adminRole = rolesService.findByName(ERoles.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            Roles userRole = rolesService.findByName(ERoles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
            listRoles.add(adminRole);
            Users users = usersService.addRoleForUser(userId);
            users.setListRoles(listRoles);
            UserResponse addedRole = usersService.addRole(users);
            return ResponseEntity.ok(addedRole);
        } else {
            return ResponseEntity.ok("not found userId");
        }
    }

    @DeleteMapping("/api/v1/admin/users/{userId}/role")
    public ResponseEntity<?> deleteRoleOfUsers(@PathVariable long userId) {
        boolean result = usersService.findUserId(userId);
        Set<Roles> listRoles = new HashSet<>();
        if (result) {
            Roles userRole = rolesService.findByName(ERoles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRoles.add(userRole);
            Users users = usersService.addRoleForUser(userId);
            users.setListRoles(listRoles);
            UserResponse addedRole = usersService.addRole(users);
            return ResponseEntity.ok(addedRole);
        } else {
            return ResponseEntity.ok("not found userId");
        }
    }


    @PutMapping("/api/v1/admin/users/{userId}")
    public ResponseEntity<String> blockOpenUsers(@PathVariable long userId) {
        boolean result = usersService.blockOpenByUserId(userId);
        if (result) {
            return ResponseEntity.ok("success");
        } else {
            return ResponseEntity.ok("fail");
        }
    }

    @GetMapping("/api/v1/admin/roles")
    public ResponseEntity<?> getListRole() {
        List<Roles> rolesList = rolesService.getListRole();
        return ResponseEntity.ok(rolesList);
    }

    @GetMapping("/api/v1/admin/users/search")
    public ResponseEntity<List<UserResponse>> searchUserByName(@PathParam("searchName") String searchName) {
        List<UserResponse> userResponseList = usersService.searchUserByName(searchName);
        return ResponseEntity.ok(userResponseList);
    }


}
