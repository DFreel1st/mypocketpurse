package com.project.mypocketpurse.controller;

import com.project.mypocketpurse.dto.AccountDTO;
import com.project.mypocketpurse.exception.MyDeleteException;
import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.service.GenericService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/accounts")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Аккаунт", description = "контроллер для работы с аккаунтами")
public class AccountController
        extends GenericController<Account> {

    private final GenericService<Account, AccountDTO> accountService;

    public AccountController(GenericService<Account, AccountDTO> accountService) {
        this.accountService = accountService;
    }

    @Operation(description = "Получить информацию об одном аккаунте по его ID",
            method = "getOne")
    @RequestMapping(value = "/getAccount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    ResponseEntity<Account> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.getOne(id));
    }

    @Operation(description = "Получить информацию обо всех аккаунтах",
            method = "getAll")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Account>> listAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountService.listAll());
    }

    @Operation(description = "Создать новый аккаунт",
            method = "add")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> add(@RequestBody AccountDTO newAccount) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createFromDTO(newAccount));
    }

    @Operation(description = "Обновить информацию по аккаунту по его id",
            method = "update")
    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDTO account,
                                               @RequestParam(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.updateFromDTO(account, id));
    }

    @Operation(description = "Удалить аккаунт по его id",
            method = "delete")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@RequestParam(value = "id") Long id) throws MyDeleteException {
        accountService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Аккаунт успешно удален");
    }
}
