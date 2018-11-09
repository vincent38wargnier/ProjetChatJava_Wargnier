package share;

import java.io.Serializable;

public class AddContentToTopicRequest implements Serializable {
    public Message content;
    public Topic topic;

    public AddContentToTopicRequest(Message content, Topic topic) {
        this.content = content;
        this.topic = topic;
    }
}
