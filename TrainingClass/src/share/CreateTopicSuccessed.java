package share;

import java.io.Serializable;

public class CreateTopicSuccessed implements Serializable {
    public String message;

    public CreateTopicSuccessed(String message) {
        this.message = message;
    }
}
