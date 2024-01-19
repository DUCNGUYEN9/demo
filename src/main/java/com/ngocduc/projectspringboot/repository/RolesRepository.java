package com.ngocduc.projectspringboot.repository;

import com.ngocduc.projectspringboot.model.entity.ERoles;
import com.ngocduc.projectspringboot.model.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(ERoles name);
}
