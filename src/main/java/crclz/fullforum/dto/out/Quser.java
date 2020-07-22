package crclz.fullforum.dto.out;

import crclz.fullforum.data.models.User;

public class Quser {
    public long id;
    public String username;

    public Quser(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public static Quser convert(User user) {
        return user == null ? null : new Quser(user.getId(), user.getUsername());
    }
}
