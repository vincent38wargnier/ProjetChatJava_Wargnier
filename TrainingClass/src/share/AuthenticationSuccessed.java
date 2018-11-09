package share;

import java.io.Serializable;
import java.util.List;

public class AuthenticationSuccessed implements Serializable {
    public String nameUser;
    public List<String> topics;
    public AuthenticationSuccessed(String nameUser, List<String> topics) {
        this.nameUser = nameUser;
        this.topics = topics;
    }
}
