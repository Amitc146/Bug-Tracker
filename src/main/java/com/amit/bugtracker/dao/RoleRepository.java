package com.amit.bugtracker.dao;

import com.amit.bugtracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String roleName);

}
