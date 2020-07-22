package crclz.fullforum.controllers;

import crclz.fullforum.data.repos.UserRepository;
import crclz.fullforum.dto.out.Quser;
import crclz.fullforum.services.IAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
