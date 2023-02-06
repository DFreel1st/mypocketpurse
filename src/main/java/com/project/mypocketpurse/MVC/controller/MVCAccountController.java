package com.project.mypocketpurse.MVC.controller;

import com.project.mypocketpurse.dto.AccountDTO;
import com.project.mypocketpurse.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/accounts")
public class MVCAccountController {

    private final AccountService accountService;

    public MVCAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("accounts", accountService.listAll());
        return "accounts/viewAllAccounts";
    }

    @GetMapping("/add")
    public String create() {
        return "accounts/addAccount";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("accountForm") @Valid AccountDTO accountDTO) {
        accountService.createFromDTO(accountDTO);
        return "redirect:/accounts";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        accountService.delete(id);
        return "redirect:/accounts";
    }

    @GetMapping("edit/{accountId}")
    public String edit(@PathVariable Long accountId,
                       Model model) {
        model.addAttribute("accounts", accountService.getOne(accountId));
        return "accounts/editAccount";
    }

    @PostMapping("edit")
    public String update(@ModelAttribute("accountForm") AccountDTO accountDTO,
                         HttpServletRequest request) {
        Long accountId = Long.valueOf(request.getParameter("id"));
        accountService.updateFromDTO(accountDTO, accountId);
        return "redirect:/accounts";
    }
}