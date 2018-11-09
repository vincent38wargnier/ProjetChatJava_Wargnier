package share;

import java.io.Serializable;

public class FindTopicFailed implements Serializable {
    public String message;

    public FindTopicFailed(String message) {
        this.message = message;
    }
}
