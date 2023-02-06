package com.project.mypocketpurse.MVC.controller;

import com.project.mypocketpurse.dto.LabelDTO;
import com.project.mypocketpurse.service.LabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping("/labels")
public class MVCLabelController {

    private final LabelService labelService;

    public MVCLabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @RequestMapping("")
    public String index(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("labels", labelService.listAll());
        return "labels/viewAllLabels";
    }

    @GetMapping("/add")
    public String create() {
        return "labels/addLabel";
    }

    @RequestMapping("/add")
    public String create(@ModelAttribute("labelForm") @Valid LabelDTO labelDTO) {
        labelService.createFromDTO(labelDTO);
        return "redirect:/labels";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        labelService.delete(id);
        return "redirect:/labels";
    }

    @GetMapping("edit/{labelId}")
    public String edit(@PathVariable Long labelId,
                       Model model) {
        model.addAttribute("labels", labelService.getOne(labelId));
        return "labels/editLabel";
    }

    @PostMapping("edit")
    public String update(@ModelAttribute("labelForm") LabelDTO labelDTO,
                         HttpServletRequest request) {
        Long labelId = Long.valueOf(request.getParameter("id"));
        labelService.updateFromDTO(labelDTO, labelId);
        return "redirect:/labels";
    }
}
