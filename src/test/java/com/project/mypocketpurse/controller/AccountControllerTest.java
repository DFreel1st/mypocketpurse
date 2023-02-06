package com.project.mypocketpurse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.mypocketpurse.dto.AccountDTO;
import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.model.CurrencyType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    public MockMvc mvc;

    //Получение информации по всем аккаунтам под админом
    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void listAllAccountsByAdmin() throws Exception {
        String result = mvc.perform(
                        get("/rest/accounts/getAll")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    //Получение информации по всем аккаунтам для одного пользователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void listAllAccountsByUser() throws Exception {
        String result = mvc.perform(
                        get("/rest/accounts/getAll")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    //Создание аккаунта пользователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void createAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName("TEST");
        accountDTO.setAmount(1000D);
        accountDTO.setDescription("Test test test");
        accountDTO.setCurrencyType(CurrencyType.RUB);

        String result = mvc.perform(
                        post("/rest/accounts/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(accountDTO))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Account account = mapper.readValue(result, Account.class);
        updateAccount(account.getId());
    }

    //Обновление информации по одному аккунту пользоователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void updateAccount(Long id) throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName("TEST UPDATED");
        accountDTO.setAmount(1000D);
        accountDTO.setDescription("TEST TEST TEST UPDATED");
        accountDTO.setCurrencyType(CurrencyType.RUB);

        String result = mvc.perform(
                        put("/rest/accounts/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(accountDTO))
                                .param("id", String.valueOf(id))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Account account = mapper.readValue(result, Account.class);
        getOneAccount(account.getId());
    }

    //Удаление аккунта пользоователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void deleteAccount(Long id) throws Exception {
        String result = mvc.perform(
                        delete("/rest/accounts/delete")
                                .param("id", String.valueOf(id))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Получение информации по одному аккаунту пользователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void getOneAccount(Long id) throws Exception {
        String result = mvc.perform(
                        get("/rest/accounts/getAccount")
                                .param("id", String.valueOf(id))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Account account = mapper.readValue(result, Account.class);
        deleteAccount(account.getId());
    }
}