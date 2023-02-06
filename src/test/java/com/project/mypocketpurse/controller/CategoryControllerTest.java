package com.project.mypocketpurse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.mypocketpurse.dto.CategoryDTO;
import com.project.mypocketpurse.model.Category;
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
public class CategoryControllerTest {

    @Autowired
    public MockMvc mvc;

    @Test
    @WithMockUser(username = "admin", password = "admin")
    public void listAllCategories() throws Exception {
        String result = mvc.perform(
                        get("/rest/categories/getAll")
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
    public void createCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("TEST");
        categoryDTO.setDescription("TEST TEST");


        String result = mvc.perform(
                        post("/rest/categories/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(categoryDTO))
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
        Category category = mapper.readValue(result, Category.class);
        updateCategory(category.getId());
    }

    //Обновление информации по одному аккунту пользоователя
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void updateCategory(Long id) throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("TEST UPDATED");
        categoryDTO.setDescription("TEST TEST UPDATED");

        String result = mvc.perform(
                        put("/rest/categories/update")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(categoryDTO))
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
        Category category = mapper.readValue(result, Category.class);
        getOneCategory(category.getId());
    }
    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void deleteCategory(Long id) throws Exception {
        String result = mvc.perform(
                        delete("/rest/categories/delete")
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

    @Test
    @WithMockUser(username = "Freel1st", password = "12345")
    public void getOneCategory(Long id) throws Exception {
        String result = mvc.perform(
                        get("/rest/categories/getCategory")
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
        Category category = mapper.readValue(result, Category.class);
        deleteCategory(category.getId());
    }
}
