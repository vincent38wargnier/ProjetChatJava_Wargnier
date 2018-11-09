package share;

import java.io.Serializable;

public class CreateTopicRequest implements Serializable {
    public String name;

    public CreateTopicRequest(String name) {
        this.name = name;
    }
}
