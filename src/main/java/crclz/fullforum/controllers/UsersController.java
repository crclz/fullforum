package crclz.fullforum.controllers;

import crclz.fullforum.dtos.CreateUserModel;
import crclz.fullforum.dtos.IdDto;
import crclz.fullforum.errhand.BadRequestException;
import crclz.fullforum.services.Snowflake;
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
    public IdDto createUser(@Valid @RequestBody CreateUserModel model) {
        var userInDb = userRepository.findByUsername(model.username);
        if (userInDb != null) {
            throw new BadRequestException("UniqueViolation", "Username already exist");
        }

        var user = new User(snowflake.nextId(), model.username, model.password);
        userRepository.save(user);

        return new IdDto(user.getId());
    }

}