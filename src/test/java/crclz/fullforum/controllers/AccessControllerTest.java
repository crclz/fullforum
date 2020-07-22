package crclz.fullforum.controllers;

import crclz.fullforum.BaseTest;
import crclz.fullforum.TestServiceConfiguration;
import crclz.fullforum.data.models.User;
import crclz.fullforum.data.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import crclz.fullforum.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.net.URI;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("classpath:unittest.properties")
@Rollback
@Transactional
@SpringBootTest
// Do not fake authentication service
//@ContextConfiguration(classes = TestServiceConfiguration.class)
@AutoConfigureMockMvc
public class AccessControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    void me_return_null_when_not_login() throws Exception {
        mockMvc.perform(get("/access/me"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));// "" is null
    }

    @Test
    void me_return_user_info_when_login() throws Exception {
        // Arrange
        var user = new User(1, "u123", "aaaaaa");
        userRepository.save(user);

        // Act

        var usernameCookie = new Cookie("username", user.getUsername());
        var passwordCookie = new Cookie("password", user.getPassword());

        mockMvc.perform(get("/access/me").cookie(usernameCookie, passwordCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", equalTo(user.getUsername())))
                .andExpect(jsonPath("$.id", equalTo(user.getId().toString())));
    }

    @Test
    void me_clear_cookie_when_wrong_cookie_username_and_password() throws Exception {
        var usernameCookie = new Cookie("username", "asddad");
        var passwordCookie = new Cookie("password", "adasdad");

        mockMvc.perform(get("/access/me").cookie(usernameCookie, passwordCookie))
                .andExpect(cookie().value("username", nullValue()))
                .andExpect(cookie().value("password", nullValue()));
    }


}
