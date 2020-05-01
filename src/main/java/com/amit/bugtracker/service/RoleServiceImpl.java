package com.amit.bugtracker.service;

import com.amit.bugtracker.dao.RoleRepository;
import com.amit.bugtracker.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

}
