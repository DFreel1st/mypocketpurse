package com.project.mypocketpurse.repository;

import com.project.mypocketpurse.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository
        extends JpaRepository<Role, Long> {
}
