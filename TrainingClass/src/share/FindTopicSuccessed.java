package share;

import java.io.Serializable;

public class FindTopicSuccessed implements Serializable {
    public Topic topic;

    public FindTopicSuccessed(Topic topic) {
        this.topic = topic;
    }
}
