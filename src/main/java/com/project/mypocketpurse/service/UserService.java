package com.project.mypocketpurse.service;

import com.project.mypocketpurse.dto.UserDTO;
import com.project.mypocketpurse.model.Role;
import com.project.mypocketpurse.model.User;
import com.project.mypocketpurse.repository.RoleRepository;
import com.project.mypocketpurse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService
        extends GenericService<User, UserDTO> {
    private static final String CHANGE_PASSWORD_URL = "http://localhost:5055/user/change-password/";

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public User updateFromDTO(UserDTO object, Long id) {
        User foundUser = getOne(id);
        foundUser.setLogin(object.getLogin());
        foundUser.setFirstName(object.getFirstName());
        foundUser.setLastName(object.getLastName());
        foundUser.setMiddleName(object.getMiddleName());
        foundUser.setBirthday(LocalDate.parse(object.getBirthday()));
        foundUser.setEmail(object.getEmail());
        foundUser.setPhone(object.getPhone());
        userRepository.save(foundUser);
        return foundUser;
    }

    @Override
    public User createFromDTO(UserDTO newDtoObject) {
        User user = new User();
        Role role;
        if (newDtoObject.getRole() == null) {
            role = roleRepository.findById(0L).get();
        } else {
            role = roleRepository.findById(newDtoObject.getRole().getId()).get();
        }
        user.setCreatedWhen(LocalDateTime.now());
        user.setCreatedBy("REGISTRATION FORM");
        user.setRoleId(role);
        user.setLogin(newDtoObject.getLogin());
        user.setPassword(bCryptPasswordEncoder.encode(newDtoObject.getPassword()));
        user.setPhone(newDtoObject.getPhone());
        user.setFirstName(newDtoObject.getFirstName());
        user.setLastName(newDtoObject.getLastName());
        user.setMiddleName(newDtoObject.getMiddleName());
        user.setEmail(newDtoObject.getEmail());
        //DATE FORMAT: YYYY-MM-DD
        user.setBirthday(LocalDate.parse(newDtoObject.getBirthday()));
        return userRepository.save(user);
    }

    @Override
    public User createFromEntity(User newObject) {
        newObject.setPassword(bCryptPasswordEncoder.encode(newObject.getPassword()));
        return userRepository.save(newObject);
    }

    @Override
    public void delete(Long objectId) {
        userRepository.deleteById(objectId);
    }

    @Override
    public User getOne(Long objectId) {
        return userRepository.findById(objectId).orElseThrow(() -> new NotFoundException("User with such ID: " + objectId + " not found in the DB"));
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }

    public UserDTO getUserByEmail(final String email) {
        return new UserDTO(userRepository.findByEmail(email));
    }

    public void sendChangePasswordEmail(final String email,
                                        final Long userId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Восстановление пароля на сервисе \"MyPocketPurse\"");
        message.setText("Добрый день! Вы получили это письмо, так как с вашего аккаунта была отправлена заявка на восстановление пароля. " +
                "Для восстановления пароля, перейдите по ссылке: '" + CHANGE_PASSWORD_URL + userId + "'");
        message.setFrom("projectmypocketpurse@gmail.com");
        javaMailSender.send(message);
    }

    public void changePassword(Long userId, String password) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with such ID: " + userId + " not found."));
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }
}
