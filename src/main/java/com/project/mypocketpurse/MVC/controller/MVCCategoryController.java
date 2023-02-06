package com.project.mypocketpurse.MVC.controller;

import com.project.mypocketpurse.dto.AccountDTO;
import com.project.mypocketpurse.dto.CategoryDTO;
import com.project.mypocketpurse.exception.MyDeleteException;
import com.project.mypocketpurse.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class MVCCategoryController {

    private final CategoryService categoryService;

    public MVCCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("categories", categoryService.listAll());
        return "categories/viewAllCategories";
    }

    @GetMapping("/add")
    public String create() {
        return "categories/addCategory";
    }

    @PostMapping("/add")
    public String create(@ModelAttribute("categoryForm") @Valid CategoryDTO categoryDTO) {
        categoryService.createFromDTO(categoryDTO);
        return "redirect:/categories";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        try {
            categoryService.delete(id);
        } catch (MyDeleteException exception) {
            return "redirect:/error/delete?message=" + exception.getMessage();
        }
        return "redirect:/categories";
    }

    @GetMapping("edit/{categoryId}")
    public String edit(@PathVariable Long categoryId,
                       Model model) {
        model.addAttribute("category", categoryService.getOne(categoryId));
        return "categories/editCategory";
    }

    @PostMapping("edit")
    public String update(@ModelAttribute("categoryForm") CategoryDTO categoryDTO,
                         HttpServletRequest request) {
        Long categoryId = Long.valueOf(request.getParameter("id"));
        categoryService.updateFromDTO(categoryDTO, categoryId);
        return "redirect:/categories";
    }
}