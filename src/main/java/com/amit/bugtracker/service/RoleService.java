package com.amit.bugtracker.service;

import com.amit.bugtracker.entity.Role;

import java.util.List;

public interface RoleService {

    Role findRoleByName(String roleName);

    List<Role> findAll();

}
