package share;

import java.io.Serializable;

public class AuthenticationFailed implements Serializable {
    public String message;

    public AuthenticationFailed(String message) {
        this.message = message;
    }
}
