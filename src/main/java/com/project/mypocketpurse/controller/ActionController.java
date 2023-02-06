package com.project.mypocketpurse.controller;

import com.project.mypocketpurse.dto.ActionDTO;
import com.project.mypocketpurse.exception.MyDeleteException;
import com.project.mypocketpurse.model.Action;
import com.project.mypocketpurse.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/actions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Действия", description = "Контроллер для работы с действиями")
public class ActionController
        extends GenericController<Action>{

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }


    @Operation(description = "Получить информацию об одном действии по его ID",
            method = "getOne")
    @RequestMapping(value = "/getAction", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    ResponseEntity<Action> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(actionService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех действиях",
            method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Action>> listAllLabels() {
        return ResponseEntity.status(HttpStatus.OK).body(actionService.listAll());
    }

    @Operation(description = "Создать новое действие",
            method = "add")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Action> add(@RequestBody ActionDTO actionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actionService.createFromDTO(actionDTO));
    }

    @Operation(description = "Обновить информацию по одному действию по его ID",
            method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Action> updateAccount(@RequestBody ActionDTO actionDTO,
                                               @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actionService.updateFromDTO(actionDTO, id));
    }

    @Operation(description = "Удалить действие по его ID",
            method = "delete")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) throws MyDeleteException {
        actionService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Действие успешно удалено");
    }
}
