package share;

import java.io.Serializable;

public class Message implements Serializable {
    public String content;
    public String autor;

    public Message(String content, String autor) {
        this.content = content;
        this.autor = autor;
    }
}
