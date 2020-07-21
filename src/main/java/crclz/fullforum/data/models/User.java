package crclz.fullforum.data.models;

import crclz.fullforum.controllers.UsersController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private Long id;
    private String username;
    private String password;

    protected User() {
    }

    public User(long id, String username, String password) {
        this.id = id;
        setUsername(username);
        setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new NullPointerException();
        }
        if (!(username.length() >= 3 && username.length() <= 16)) {
            throw new IllegalArgumentException("username length should in [3,16]");
        }

        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            throw new NullPointerException();
        }
        if (!(password.length() >= 6 && password.length() <= 32)) {
            throw new IllegalArgumentException();
        }
        this.password = password;
    }
}