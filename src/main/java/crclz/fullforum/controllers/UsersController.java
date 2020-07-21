package crclz.fullforum.controllers;

import crclz.fullforum.data.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    Snowflake snowflake;

    @GetMapping("nextid")
    public long nextId() {
        return Long.MAX_VALUE;
    }
}
