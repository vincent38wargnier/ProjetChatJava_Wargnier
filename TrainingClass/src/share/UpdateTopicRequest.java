package share;

import java.io.Serializable;

public class UpdateTopicRequest implements Serializable {
    public Topic topic;

    public UpdateTopicRequest(Topic topic) {
        this.topic = topic;
    }
}
