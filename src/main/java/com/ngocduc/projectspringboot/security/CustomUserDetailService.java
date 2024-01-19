package com.ngocduc.projectspringboot.security;

import com.ngocduc.projectspringboot.model.entity.Users;
import com.ngocduc.projectspringboot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUserNameAndStatus(username,true);
        if (user==null){
            throw new UsernameNotFoundException("User not found or blocked");
        }
//        System.out.println(CustomUserDetail.mapUserToUserDetail(user));
        return CustomUserDetail.mapUserToUserDetail(user);
    }
}
