package com.jewelry.managementsystem.repositories;

import com.jewelry.managementsystem.models.Role;
import com.jewelry.managementsystem.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {

    Optional<Role> findByRolename(Roles roleName);


}
