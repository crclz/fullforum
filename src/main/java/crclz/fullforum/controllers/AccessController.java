package crclz.fullforum.controllers;

import crclz.fullforum.data.repos.UserRepository;
import crclz.fullforum.dto.in.LoginModel;
import crclz.fullforum.dto.out.Quser;
import crclz.fullforum.errhand.BadRequestException;
import crclz.fullforum.errhand.ErrorCode;
import crclz.fullforum.services.IAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("access")
public class AccessController {

    @Autowired
    IAuth auth;

    @Autowired
    UserRepository userRepository;


    @GetMapping("me")
    public Quser me() {
        if (!auth.isLoggedIn()) {
            return null;
        }

        var user = userRepository.findById(auth.userId()).orElseThrow();

        return Quser.convert(user);
    }

    @PostMapping("login")
    public void login(@RequestBody @Valid LoginModel model, HttpServletResponse response) {
        if (response == null) {
            throw new NullPointerException();
        }
        var user = userRepository.findByUsername(model.username);
        if (user == null) {
            throw new BadRequestException(ErrorCode.UsernameNotExist, "Username not exist");
        }
        if (!user.checkPassword(model.password)) {
            throw new BadRequestException(ErrorCode.WrongPassword, "Password incorrect");
        }

        // username password ok
        // set cookie
        int maxage = (int) Duration.ofDays(180).toSeconds();
        var usernameCookie = new Cookie("username", model.username);
        usernameCookie.setMaxAge(maxage);
        response.addCookie(usernameCookie);

        var passwordCookie = new Cookie("password", model.password);
        passwordCookie.setMaxAge(maxage);
        response.addCookie(passwordCookie);
    }
}
