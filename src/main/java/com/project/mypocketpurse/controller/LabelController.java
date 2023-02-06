package com.project.mypocketpurse.controller;

import com.project.mypocketpurse.dto.LabelDTO;
import com.project.mypocketpurse.exception.MyDeleteException;
import com.project.mypocketpurse.model.Label;
import com.project.mypocketpurse.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/labels")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Метки", description = "контроллер для работы с метками")
public class LabelController
        extends GenericController<Label>{

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @Operation(description = "Получить информацию об одной метке по её ID",
            method = "getOne")
    @RequestMapping(value = "/getLabel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    ResponseEntity<Label> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(labelService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех метках",
            method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Label>> listAllLabels() {
        return ResponseEntity.status(HttpStatus.OK).body(labelService.listAll());
    }

    @Operation(description = "Создать новую метку",
            method = "add")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Label> add(@RequestBody LabelDTO labelDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labelService.createFromDTO(labelDTO));
    }

    @Operation(description = "Обновить информацию по одной метке по её id",
            method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Label> updateAccount(@RequestBody LabelDTO labelDTO,
                                                  @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labelService.updateFromDTO(labelDTO, id));
    }

    @Operation(description = "Удалить метку по её id",
            method = "delete")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) throws MyDeleteException {
        labelService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Метка успешно удалена");
    }
}
