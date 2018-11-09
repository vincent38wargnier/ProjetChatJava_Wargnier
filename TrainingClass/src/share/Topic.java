package share;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable {
    public String name;
    public List<Message> messages = new ArrayList<>();
    public Topic(String name) {

        this.name = name;
        //messages.add(new Message("welcome in this topic!", "System"));
    }
}
