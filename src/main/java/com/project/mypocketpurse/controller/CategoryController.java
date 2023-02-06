package com.project.mypocketpurse.controller;

import com.project.mypocketpurse.dto.CategoryDTO;
import com.project.mypocketpurse.exception.MyDeleteException;
import com.project.mypocketpurse.model.Category;
import com.project.mypocketpurse.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Категории", description = "контроллер для работы с категориями")
public class CategoryController
        extends GenericController<Category>{

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(description = "Получить информацию об одной категоррии по её ID",
            method = "getOne")
    @RequestMapping(value = "/getCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    ResponseEntity<Category> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех категориях",
            method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> listAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.listAll());
    }

    @Operation(description = "Создать новую категорию",
            method = "add")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> add(@RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createFromDTO(categoryDTO));
    }

    @Operation(description = "Обновить информацию по одной категории по её id",
            method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> updateAccount(@RequestBody CategoryDTO categoryDTO,
                                                 @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.updateFromDTO(categoryDTO, id));
    }

    @Operation(description = "Удалить категорию по её id",
            method = "delete")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) throws MyDeleteException {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Категория успешно удалена");
    }
}
