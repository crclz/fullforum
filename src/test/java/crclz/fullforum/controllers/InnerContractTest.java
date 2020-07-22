package crclz.fullforum.controllers;

import crclz.fullforum.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.net.URI;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public class InnerContractTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void badRequest_400() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=BAD_REQUEST"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("a"));
    }

    @Test
    void unauthorized_401() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=UNAUTHORIZED"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void forbidden_403() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=FORBIDDEN"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void forbidden_404() throws Exception {
        mockMvc.perform(
                post(new URI("/users/report-error?status=NOT_FOUND"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
