package com.project.mypocketpurse.repository;

import com.project.mypocketpurse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    User findUserByLogin(String login);

    User findByEmail(String email);
}
