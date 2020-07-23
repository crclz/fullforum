package crclz.fullforum.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crclz.fullforum.BaseTest;
import crclz.fullforum.dependency.FakeAuth;
import crclz.fullforum.dto.in.CreateUserModel;
import crclz.fullforum.dto.in.PatchUserModel;
import crclz.fullforum.errhand.BadRequestException;
import crclz.fullforum.errhand.UnauthorizedException;
import crclz.fullforum.services.Snowflake;
import crclz.fullforum.data.models.User;
import crclz.fullforum.data.repos.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PatchMapping;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.hamcrest.Matchers.*;


class UsersControllerTest extends BaseTest {

    @Autowired
    UsersController usersController;

    @Autowired
    Snowflake snowflake;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FakeAuth auth;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createUser_throw_Model_when_model_is_invalid() {
        var model = new CreateUserModel();
        model.username = "user";
        model.password = "ad";
        assertThrows(IllegalArgumentException.class, () -> usersController.createUser(model));
    }

    @Test
    void createUser_throw_BadRequestExcepton_when_username_exist() {
        // Arrange: insert a user with username 'user'
        var previousUser = new User(snowflake.nextId(), "user", "aaaaaaaa");
        usersController.userRepository.save(previousUser);

        // Act & Assert
        var model = new CreateUserModel();
        model.username = "user";
        model.password = "adaqdadsada";

        assertThrows(BadRequestException.class, () -> usersController.createUser(model));
    }

    @Test
    void createUser_return_id_and_update_db_when_all_ok() {
        // Act
        var model = new CreateUserModel();
        model.username = "user";
        model.password = "adaqdadsada";
        var idw = usersController.createUser(model);

        // Assert

        assertNotNull(idw);
        var userId = idw.getLongId();

        // Check db
        userRepository.flush();
        var users = userRepository.findAll();
        assertTrue(users.size() > 0);
        var userInDb = userRepository.findById(userId).orElse(null);
        assertNotNull(userInDb);
        assertEquals(userId, userInDb.getId());
        assertEquals(model.username, userInDb.getUsername());
        assertEquals(model.password, userInDb.getPassword());
    }

    @Autowired
    private ObjectMapper mapper;

    @Test
    void patchUser_return_bad_request_when_model_invalid() throws Exception {
        var model = new PatchUserModel("o234a");

        var r = mockMvc.perform(
                patch("/users/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(model)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
        assertThat(r.getResolvedException()).isExactlyInstanceOf(MethodArgumentNotValidException.class);
    }

    @Test
    void patchUser_return_unauthorized_when_not_login() {
        var model = new PatchUserModel("o234a6");
        assertThrows(UnauthorizedException.class, () -> usersController.patchUser(model, 1));
    }

    @Test
    void patchUser_return_forbidden_when_target_user_is_not_self() {

    }

    @Test
    void patchUser_return_ok_and_changes_db_when_all_ok() {

    }
}