package fullforum.services;

import fullforum.data.repos.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class Auth implements IAuth {
    private long userId = -1;
    private boolean loaded = false;

    private HttpServletRequest request;
    private UserRepository userRepository;
    private HttpServletResponse response;

    public Auth(HttpServletRequest request, HttpServletResponse response, UserRepository userRepository) {
        this.request = request;
        this.response = response;
        this.userRepository = userRepository;
    }

    private boolean innerload() {
        if (loaded) {
            throw new IllegalStateException();
        }
        var cookies = request.getCookies();
        if (cookies == null) {
            return false;
        }
        var username = Arrays.stream(cookies)
                .filter(p -> p.getName().equals("username"))
                .map(Cookie::getValue)
                .findFirst().orElse(null);

        var password = Arrays.stream(cookies)
                .filter(p -> p.getName().equals("password"))
                .map(Cookie::getValue)
                .findFirst().orElse(null);

        if (username == null || password == null) {
            return false;
        }

        var user = userRepository.findByUsername(username);

        if (user == null) {
            return false;
        }

        if (!user.checkPassword(password)) {
            return false;
        }

        // username and password matches
        userId = user.getId();

        return true;
    }

    private void load() {
        var success = innerload();
        loaded = true;
        // if not success, clear some cookie
        if (!success) {
            response.addCookie(new Cookie("username", null));
            response.addCookie(new Cookie("password", null));
        }
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
