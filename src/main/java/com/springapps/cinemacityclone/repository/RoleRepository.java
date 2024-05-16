package com.springapps.cinemacityclone.repository;

import com.springapps.cinemacityclone.model.Role;
import com.springapps.cinemacityclone.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleType(RoleType roleType);
}
