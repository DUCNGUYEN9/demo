package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.entity.Address;
import com.ngocduc.projectspringboot.model.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findAllByUsersIs(Users users);
    @Query("select a from Address a where a.users.id= :userId and a.address_id = :addressId")
    Address findByUsers_IdAndAddress_id(long userId,long addressId);
}
