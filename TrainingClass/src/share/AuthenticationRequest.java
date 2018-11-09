package share;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    public String username;
    public String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
