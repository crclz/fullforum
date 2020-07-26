package fullforum.dto.out;

import fullforum.data.models.User;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data// ModelMapper需要getter和setter
public class Quser {
    public long id;
    public String username;

    public static Quser convert(User user, ModelMapper mapper) {
        if (user == null) {
            return null;
        }

        return mapper.map(user, Quser.class);
    }

    public static void main(String[] args) {
        var m = new ModelMapper();

        var user = new User(1, "aaa", "asdsadsad");
        var q = m.map(user, Quser.class);
    }
}
