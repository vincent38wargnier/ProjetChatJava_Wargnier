package share;

import java.io.Serializable;

public class CreateAccountSuccess implements Serializable {
    public  String message;

    public CreateAccountSuccess(String message) {
        this.message = message;
    }
}
