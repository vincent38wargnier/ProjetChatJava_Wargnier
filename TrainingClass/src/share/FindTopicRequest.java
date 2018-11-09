package share;

import java.io.Serializable;

public class FindTopicRequest implements Serializable {
    public String name;

    public FindTopicRequest(String name) {
        this.name = name;
    }
}
