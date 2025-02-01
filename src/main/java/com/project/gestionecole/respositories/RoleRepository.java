package com.project.gestionecole.respositories;

import com.project.gestionecole.models.Role;
import com.project.gestionecole.models.RoleNames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleNames roleName);
}
