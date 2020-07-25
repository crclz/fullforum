package fullforum.dto.out;

import fullforum.data.models.User;

public class Quser {
    public String id;
    public String username;

    public Quser(Long id, String username) {
        this.id = id.toString();
        this.username = username;
    }

    public static Quser convert(User user) {
        return user == null ? null : new Quser(user.getId(), user.getUsername());
    }

    public long getLongId() {
        return Long.parseLong(id);
    }
}
