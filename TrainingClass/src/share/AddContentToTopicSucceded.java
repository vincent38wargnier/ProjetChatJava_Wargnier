package share;

import java.io.Serializable;

public class AddContentToTopicSucceded implements Serializable {
    public Topic newTopic;
    public String message;
    public AddContentToTopicSucceded(Topic newTopic) {
        this.newTopic = newTopic;
        message = "Message added to the topic " + this.newTopic.name + " successfully! (size : " + newTopic.messages.size();
    }
}
