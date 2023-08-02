package com.example.controller.rest;

import com.example.controller.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.service.util.ConstContainer.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;

    @Test
    public void testAddUser_SuccessfulCreation() throws Exception {
        String requestContent = "{\"name\":\"John\",\"surname\":\"Doe\",\"patronymic\":\"Smith\"," +
                                "\"email\":\"josfshn@gmail.com\",\"role\":\"SALE_USER\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(USERS_LINK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddUser_InvalidEmailFormat() throws Exception {
        String requestContent = "{\"name\":\"John\",\"surname\":\"Doe\",\"patronymic\":\"Smith\"," +
                                "\"email\":\"invalid_email\",\"role\":\"SALE_USER\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(USERS_LINK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddUser_MissingRequiredFields() throws Exception {
        String requestContent = "{\"name\":\"John\",\"surname\":\"\",\"patronymic\":\"Smith\"," +
                                "\"email\":\"josfshn@gmail.com\",\"role\":\"SALE_USER\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(USERS_LINK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddUser_InvalidData() throws Exception {
        String requestContent = "{\"name\":\"Joh42n\",\"surname\":\"42421\",\"patronymic\":\"Smith\"," +
                                "\"email\":\"emily.williams@example.com\",\"role\":\"SALE_USER\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(USERS_LINK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testAddUser_DuplicateEmail() throws Exception {
        String requestContent = "{\"name\":\"John\",\"surname\":\"\",\"patronymic\":\"Smith\"," +
                                "\"email\":\"emily.williams@example.com\",\"role\":\"SALE_USER\"}";
        mockMvc.perform(MockMvcRequestBuilders.post(USERS_LINK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(VALIDATION_FAILED));
    }

    @Test
    public void testGetUsersByPage_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(USERS_LINK + BY_PAGE)
                        .param(PAGE, ZERO)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content.length()").value(10));
    }

    @Test
    public void testGetUsersByPage_DefaultPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(USERS_LINK + BY_PAGE)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content.length()").value(10));
    }


    @Test
    public void testGetUsersByPage_EmptyResult() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(USERS_LINK + BY_PAGE)
                        .param(PAGE, TEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.content.length()").value(0));
    }
}