package com.schefer.agenda.controller;

import com.schefer.agenda.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthService authService;

    @Test
    void DeveraRetornarStatus200ETokenParaLoginValido() throws Exception {

        // arrange
        String json = """
                {
                  "id": 1,
                  "senha": "123456"
                }
                """;

        when(authService.login(any()))
                .thenReturn(new AuthController.LoginResponse("token-fake-jwt"));

        // act
        var response = mvc.perform(
                post("/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        assertEquals(200, response.getStatus());
    }

    @Test
    void DeveraRetornarStatus400ParaLoginComBodyVazio() throws Exception {

        // arrange
        String json = "{}";

        // act
        var response = mvc.perform(
                post("/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        assertEquals(400, response.getStatus());
    }

    @Test
    void DeveraRetornarStatus400ParaLoginSemSenha() throws Exception {

        // arrange
        String json = """
                {
                  "id": 1
                }
                """;

        // act
        var response = mvc.perform(
                post("/auth/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        // assert
        assertEquals(400, response.getStatus());
    }
}