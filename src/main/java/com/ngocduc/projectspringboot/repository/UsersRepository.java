package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.dto.response.UserResponse;
import com.ngocduc.projectspringboot.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByUserNameAndStatus(String userName, boolean status);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    Users findByUserName(String userName);
    @Query("select u from Users u where u.fullName like %:name%")
    List<Users> searchUserByName(String name);

}
