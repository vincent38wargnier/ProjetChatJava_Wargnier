package share;

import java.io.Serializable;

public class CreateTopicDenied implements Serializable {
    public String message;

    public CreateTopicDenied(String message) {
        this.message = message;
    }
}
