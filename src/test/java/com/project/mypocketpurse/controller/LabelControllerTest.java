package com.project.mypocketpurse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.mypocketpurse.dto.AccountDTO;
import com.project.mypocketpurse.dto.LabelDTO;
import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.model.CurrencyType;
import com.project.mypocketpurse.model.Label;
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
public class LabelControllerTest {

    @Autowired
    public MockMvc mvc;

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void listAllLabelsByAdmin() throws Exception {
        String result = mvc.perform(
                        get("/rest/labels/getAll")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void listAllLabelsByUser() throws Exception {
        String result = mvc.perform(
                        get("/rest/labels/getAll")
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
    public void createLabel() throws Exception {
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("TEST");
        labelDTO.setDescription("TEST TEST TEST");

        String result = mvc.perform(
                        post("/rest/labels/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(labelDTO))
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
        Label label = mapper.readValue(result, Label.class);
        updateLabel(label.getId());
    }

    //Обновление информации по одному аккунту пользоователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void updateLabel(Long id) throws Exception {
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setName("TEST UPDATED");
        labelDTO.setDescription("TEST TEST TEST UPDATED");

        String result = mvc.perform(
                        put("/rest/labels/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(labelDTO))
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
        Label label = mapper.readValue(result, Label.class);
        getOneLabel(label.getId());
    }

    //Удаление аккунта пользоователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void deleteLabel(Long id) throws Exception {
        String result = mvc.perform(
                        delete("/rest/labels/delete")
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
    public void getOneLabel(Long id) throws Exception {
        String result = mvc.perform(
                        get("/rest/labels/getLabel")
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
        Label label = mapper.readValue(result, Label.class);
        deleteLabel(label.getId());
    }
}
