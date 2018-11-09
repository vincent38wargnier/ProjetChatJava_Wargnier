package share;

import java.io.Serializable;

public class AddContentToTopicFailed implements Serializable {
    public String message;

    public AddContentToTopicFailed(String message) {
        this.message = message;
    }
}
