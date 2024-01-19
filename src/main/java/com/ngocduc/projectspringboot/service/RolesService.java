package com.ngocduc.projectspringboot.service;

import com.ngocduc.projectspringboot.model.entity.ERoles;
import com.ngocduc.projectspringboot.model.entity.Roles;

import java.util.List;
import java.util.Optional;

public interface RolesService {
    Optional<Roles> findByName(ERoles name);
    List<Roles> getListRole();
}
