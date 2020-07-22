package crclz.fullforum.controllers;

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

import static org.junit.jupiter.api.Assertions.*;

class UsersControllerTest extends BaseTest {

    @Autowired
    UsersController usersController;

    @Autowired
    Snowflake snowflake;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FakeAuth auth;

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
        var userId = idw.id;

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

    @Test
    void A() {
        auth.realUserId = -1;
        var model = new PatchUserModel();
        model.password = "asdadsa";

        assertThrows(UnauthorizedException.class, () -> usersController.patchUser(model, 1));
    }
}