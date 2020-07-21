package crclz.fullforum.controllers;

import crclz.fullforum.controllers.dtos.CreateUserModel;
import crclz.fullforum.controllers.dtos.IdDto;
import crclz.fullforum.controllers.errhand.BadRequestException;
import crclz.fullforum.data.Snowflake;
import crclz.fullforum.data.models.User;
import crclz.fullforum.data.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    Snowflake snowflake;

    @Autowired
    UserRepository userRepository;


    @PostMapping
    public IdDto CreateUser(@Valid @RequestBody CreateUserModel model) {
        var userInDb = userRepository.findByUsername(model.username);
        if (userInDb != null) {
            throw new BadRequestException("UniqueViolation", "Username already exist");
        }

        var user = new User(snowflake.nextId(), model.username, model.password);
        userRepository.save(user);

        return new IdDto(snowflake.nextId());
    }

}