package com.ngocduc.projectspringboot.controller.user;

import com.ngocduc.projectspringboot.model.dto.request.AddressRequest;
import com.ngocduc.projectspringboot.model.dto.request.ChangePasswordRequest;
import com.ngocduc.projectspringboot.model.dto.request.UpdateUser;
import com.ngocduc.projectspringboot.model.dto.request.UserRequest;
import com.ngocduc.projectspringboot.model.dto.response.FileInfoResponse;
import com.ngocduc.projectspringboot.security.CustomUserDetail;
import com.ngocduc.projectspringboot.service.AddressService;
import com.ngocduc.projectspringboot.service.FileUploadService;
import com.ngocduc.projectspringboot.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;
import java.util.*;

@RestController
public class UserControllerAccount {

    @Autowired
    private UsersService usersService;
    @Autowired
    private AddressService addressService;
    //update image user
    @Autowired
    private FileUploadService fileUploadService;
    @PostMapping("/api/v1/account/avatar")
    public ResponseEntity<List<FileInfoResponse>> uploadFile(@RequestBody MultipartFile[] files){
        fileUploadService.init();
        Map<String, Path> listPath = new HashMap<>();
        Arrays.asList(files).forEach(file->{
            Path path = fileUploadService.uploadFile(file);
            listPath.put(file.getOriginalFilename(),path);
        });
        List<FileInfoResponse> listFileInfo = new ArrayList<>();
        for (String key:listPath.keySet()) {
            //UserController
            String url = MvcUriComponentsBuilder.fromMethodName(UserControllerAccount.class,"getFile",
                    listPath.get(key).getFileName().toString()).build().toString();
            listFileInfo.add(new FileInfoResponse(key,url));
        }
        return ResponseEntity.status(HttpStatus.OK).body(listFileInfo);
    }
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName){
        Resource file = fileUploadService.load(fileName);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=\""+file.getFilename()+"\"").body(file);
    }
    //

    @GetMapping("/api/v1/account")
    public long returnUserIdAuthentication(){
        return ((CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

    @GetMapping("/api/v1/account/{userId}")
    public ResponseEntity<?> infoAccountById(@PathVariable long userId) {
        return ResponseEntity.ok(usersService.getInfoUser(userId));
    }

    @PutMapping("/api/v1/account/{userId}")
    public ResponseEntity<?> UpdateInfoAccountById(@PathVariable long userId, @RequestBody UpdateUser updateUser) {

        return ResponseEntity.ok(usersService.updateInfoUser(userId,updateUser));
    }

    @PutMapping("/api/v1/account/{userId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable long userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
       usersService.changePassword(userId,changePasswordRequest);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/api/v1/account/{userId}/address")
    public ResponseEntity<?> addNewAddress(@PathVariable long userId, @RequestBody AddressRequest addressRequest) {

        return ResponseEntity.ok(addressService.addNew(userId,addressRequest));
    }

    @GetMapping("/api/v1/account/{userId}/address")
    public ResponseEntity<?> getListAddressById(@PathVariable long userId) {
        return ResponseEntity.ok(addressService.getAllListAddress(userId));
    }

    @GetMapping("/api/v1/account/{userId}/address/{addressId}")
    public ResponseEntity<?> getAddressByAddressId(@PathVariable long userId,@PathVariable long addressId) {

        return ResponseEntity.ok(addressService.findByUsers_IdAndAddress_id(userId,addressId));
    }

    @GetMapping("/api/v1/account/{userId}/history")
    public ResponseEntity<?> getListHistoryOrder(@PathVariable long userId) {

        return ResponseEntity.ok(usersService.getListHistoryOrder(userId));
    }

    @GetMapping("/api/v1/account/{userId}/history/{orderStatus}")
    public ResponseEntity<?> getListHistoryByOrderStatus(@PathVariable long userId,@PathVariable String orderStatus) {

        return ResponseEntity.ok(usersService.findAllByUsersAndAndOrder_status(userId,orderStatus));
    }

    //status: waiting -> cancel
    @PutMapping("/api/v1/account/{userId}/history/{orderId}/cancel")
    public ResponseEntity<?> cancel(@PathVariable long userId,@PathVariable long orderId) {

        return ResponseEntity.ok(usersService.cancel(userId,orderId));
    }





}
