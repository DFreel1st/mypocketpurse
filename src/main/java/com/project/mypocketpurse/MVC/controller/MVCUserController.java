package com.project.mypocketpurse.MVC.controller;

import com.project.mypocketpurse.dto.UserDTO;
import com.project.mypocketpurse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/user")
public class MVCUserController {
    private final UserService userService;

    public MVCUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest, Model model) {
        return "index";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("userForm") UserDTO userDTO) {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@Valid @ModelAttribute("userForm") UserDTO userDTO,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.createFromDTO(userDTO);
            return "redirect:login";
        }
    }

    @GetMapping("/remember-password")
    public String changePassword() {
        return "rememberPassword";
    }

    @PostMapping("/remember-password")
    public String changePassword(@ModelAttribute("changePasswordForm") @Valid UserDTO userDTO) {
//        log.info("!!!Changing password!!!");
        userDTO = userService.getUserByEmail(userDTO.getEmail());
        userService.sendChangePasswordEmail(userDTO.getEmail(), userDTO.getId());
        return "redirect:/login";
    }

    @GetMapping("/change-password/{userId}")
    public String changePasswordAfterEmailSent(@PathVariable Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "changePassword";
    }

    @PostMapping("/change-password/{userId}")
    public String changePassword(@PathVariable Long userId,
                                 @ModelAttribute("changePasswordForm") @Valid UserDTO userDTO) {
        userService.changePassword(userId, userDTO.getPassword());
        return "redirect:/login";
    }

    @GetMapping("profile/{userId}")
    public String profile(@PathVariable Long userId,
                          Model model) {
        model.addAttribute("user", userService.getOne(userId));
        return "/user/viewProfile";
    }

    @GetMapping("profile/update/{userId}")
    public String update(@PathVariable Long userId,
                         Model model) {
        model.addAttribute("user", userService.getOne(userId));
        return "/user/updateProfile";
    }

    @PostMapping("profile/update")
    public String update(@ModelAttribute("userForm") UserDTO userDTO,
                         HttpServletRequest request) {
        Long userId = Long.valueOf(request.getParameter("id"));
        userService.updateFromDTO(userDTO, userId);
        return "redirect:/user/profile/" + userId;
    }
}
