package com.project.mypocketpurse.controller;

import com.project.mypocketpurse.dto.UserDTO;
import com.project.mypocketpurse.exception.MyDeleteException;
import com.project.mypocketpurse.model.User;
import com.project.mypocketpurse.service.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController
        extends GenericController<User>{

    private final GenericService<User, UserDTO> userService;

    public UserController(GenericService<User, UserDTO> userService) {
        this.userService = userService;
    }

    @Operation(description = "Получить информацию об одном пользователе по его ID",
            method = "getOne")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    ResponseEntity<User> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех пользователях",
            method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.listAll());
    }

    @Operation(description = "Создать нового пользователя",
            method = "registration")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> add(@RequestBody UserDTO newUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createFromDTO(newUser));
    }

    @Operation(description = "Обновить информацию по пользователю по его id",
            method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateAccount(@RequestBody UserDTO user,
                                                 @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateFromDTO(user, id));
    }

    @Operation(description = "Удалить пользователя по его id",
            method = "delete")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) throws MyDeleteException {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Пользователь успешно удален");
    }
}
