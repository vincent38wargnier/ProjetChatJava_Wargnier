package share;

import java.io.Serializable;

public class CreateAccountDenied implements Serializable {
    public String Message;

    public CreateAccountDenied(String message) {
        Message = message;
    }
}
