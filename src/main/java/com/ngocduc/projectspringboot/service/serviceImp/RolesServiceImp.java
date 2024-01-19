package com.ngocduc.projectspringboot.service.serviceImp;

import com.ngocduc.projectspringboot.model.entity.ERoles;
import com.ngocduc.projectspringboot.model.entity.Roles;
import com.ngocduc.projectspringboot.repository.RolesRepository;
import com.ngocduc.projectspringboot.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesServiceImp implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public Optional<Roles> findByName(ERoles name) {
        return rolesRepository.findByName(name);
    }

    @Override
    public List<Roles> getListRole() {
        return rolesRepository.findAll();
    }
}
