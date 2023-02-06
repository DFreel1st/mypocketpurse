package com.project.mypocketpurse.MVC.controller;

import com.project.mypocketpurse.dto.ActionDTO;
import com.project.mypocketpurse.dto.ActionSearchDTO;
import com.project.mypocketpurse.model.Action;
import com.project.mypocketpurse.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/actions")
public class MVCActionController {
    private final ActionService actionService;
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final LabelService labelService;
    private final UserService userService;

    public MVCActionController(ActionService actionService,
                               AccountService accountService,
                               CategoryService categoryService,
                               LabelService labelService,
                               UserService userService) {
        this.actionService = actionService;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.labelService = labelService;
        this.userService = userService;
    }

    @RequestMapping("")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        HttpServletRequest httpServletRequest,
                        Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "dateTime"));
        Page<Action> result = actionService.getAllPaginated(pageRequest);
//        model.addAttribute("actions", actionService.listAll());
        model.addAttribute("actions", result);
        model.addAttribute("labels", labelService.listAll());
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("accounts", accountService.listAll());
//        model.addAttribute("users", userService.listAll());
        model.addAttribute("actionSearchForm", new ActionDTO());
        return "actions/viewAllActions";
    }

    @GetMapping("/add")
    public String create(Model model) {
        model.addAttribute("accounts", accountService.listAll());
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("labels", labelService.listAll());
        model.addAttribute("actionForm", new ActionDTO());
        return "actions/addAction";
    }

    @RequestMapping("/add")
    public String create(@ModelAttribute("actionForm") @Valid ActionDTO actionDTO) {
        actionService.createFromDTO(actionDTO);
        return "redirect:/actions";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        actionService.delete(id);
        return "redirect:/actions";
    }

    @PostMapping("/search")
    public String searchActions(@ModelAttribute("actionSearchForm") @Valid ActionSearchDTO actionSearchDTO,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "date_time"));
        model.addAttribute("labels", labelService.listAll());
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("accounts", accountService.listAll());
//        model.addAttribute("users", userService.listAll());
//        model.addAttribute("actionSearchForm", new ActionSearchDTO());
        model.addAttribute("actions", actionService.searchActions(actionSearchDTO, pageRequest));
        return "actions/viewAllActions";
    }
}