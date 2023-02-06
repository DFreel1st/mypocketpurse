package com.project.mypocketpurse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.mypocketpurse.dto.ActionDTO;
import com.project.mypocketpurse.dto.LabelDTO;
import com.project.mypocketpurse.model.*;
import com.project.mypocketpurse.repository.LabelRepository;
import com.project.mypocketpurse.service.AccountService;
import com.project.mypocketpurse.service.CategoryService;
import com.project.mypocketpurse.service.LabelService;
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
public class ActionControllerTest {

    @Autowired
    public MockMvc mvc;

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void listAllActionsByAdmin() throws Exception {
        String result = mvc.perform(
                        get("/rest/actions/getAll")
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
    public void listAllActionsByUser() throws Exception {
        String result = mvc.perform(
                        get("/rest/actions/getAll")
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
    public void deleteAction() throws Exception {
        String result = mvc.perform(
                        delete("/rest/actions/delete")
                                .param("id", "53")
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

    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void getOneAction() throws Exception {
        String result = mvc.perform(
                        get("/rest/actions/getAction")
                                .param("id", "53")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
