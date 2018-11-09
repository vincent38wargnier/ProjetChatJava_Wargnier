package share;

import java.io.Serializable;

public class CreateAccountRequest implements Serializable {
    public String name, password;

    public CreateAccountRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
