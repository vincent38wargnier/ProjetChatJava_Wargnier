package share;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    public String name;
    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
