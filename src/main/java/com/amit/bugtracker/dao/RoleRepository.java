package com.amit.bugtracker.dao;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(nativeQuery = true, value = "SELECT role_id as label, COUNT(*) as value " +
            "FROM users_roles " +
            "GROUP BY role_id")
    List<ChartData> getRolesCount();

    Role findRoleByName(String roleName);

}
