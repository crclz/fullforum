package crclz.fullforum.controllers;

import crclz.fullforum.dtos.CreateUserModel;
import crclz.fullforum.dtos.IdDto;
import crclz.fullforum.dtos.PatchUserModel;
import crclz.fullforum.errhand.*;
import crclz.fullforum.services.Snowflake;
import crclz.fullforum.data.models.User;
import crclz.fullforum.data.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

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
            throw new BadRequestException(ErrorCode.UniqueViolation, "Username already exist");
        }

        var user = new User(snowflake.nextId(), model.username, model.password);
        userRepository.save(user);

        return new IdDto(user.getId());
    }

    @PatchMapping("{id}")
    public void patchUser(@Valid @RequestBody PatchUserModel model, @PathVariable long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new NotFoundException();
        }
    }

    @PostMapping("report-error")
    public void reportError(@RequestParam HttpStatus status) {
        switch (status) {
            case BAD_REQUEST:
                // 400
                throw new BadRequestException(ErrorCode.UniqueViolation, "a");
            case UNAUTHORIZED:
                // 401
                throw new UnauthorizedException();
            case FORBIDDEN:
                // 403
                throw new ForbidException();
            case NOT_FOUND:
                // 404
                throw new NotFoundException();
            default:
                throw new IllegalArgumentException("Unexpected status: " + status);
        }
    }
}