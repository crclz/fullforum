package crclz.fullforum.services;

import crclz.fullforum.data.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Auth implements IAuth {
    private long userId = -1;
    private boolean loaded = false;

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserRepository userRepository;

    private void load() {
        if (loaded) {
            throw new IllegalStateException();
        }

        var username = Arrays.stream(request.getCookies())
                .filter(p -> p.getName().equals("username"))
                .map(Cookie::getValue)
                .findFirst().orElse(null);

        var password = Arrays.stream(request.getCookies())
                .filter(p -> p.getName().equals("password"))
                .map(Cookie::getValue)
                .findFirst().orElse(null);

        if (username == null || password == null) {
            return;
        }

        var user = userRepository.findByUsername(username);

        if (user == null) {
            return;
        }

        if (!user.checkPassword(password)) {
            return;
        }

        // username and password matches
        userId = user.getId();

        loaded = true;
    }

    @Override
    public boolean isLoggedIn() {
        if (!loaded) {
            load();
        }
        return userId != -1;
    }

    @Override
    public long userId() {
        if (!loaded) {
            load();
        }
        if (!isLoggedIn()) {
            throw new IllegalStateException();
        }
        return userId;
    }
}
