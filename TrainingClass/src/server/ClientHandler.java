package server;

import share.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientHandler extends Thread {
    Server theServer;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    ClientListener myListener;
    public ClientHandler(Server theServer, Socket socketClient) throws IOException {
        this.theServer = theServer;
        ois = new ObjectInputStream(socketClient.getInputStream());
        oos = new ObjectOutputStream(socketClient.getOutputStream());
        myListener = new ClientListener(this, ois);
        myListener.start();
    }

    @Override
    public void run() {
        String message = "bonjour vous etes connecté\npress a key to start";
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReportAMessage(Object request) throws IOException, InterruptedException {
        if(request instanceof AuthenticationRequest)
        {
            AuthenticationRequest req = (AuthenticationRequest) request;
            if(theServer.getData().users.stream().filter(User -> User.name.equals(req.username)).findFirst().isPresent())
            {
                if(theServer.getData().users.stream().filter(User -> User.password.equals(req.password)).findFirst().isPresent())
                {
                    List<String> topicsNames = new ArrayList<>();
                    for (Topic t:theServer.data.topics) {
                        topicsNames.add(t.name);
                    }
                    oos.writeObject(new AuthenticationSuccessed(req.username, topicsNames));
                }
                else
                {
                    oos.writeObject(new AuthenticationFailed("Authentification failed"));
                }
            }
            else
            {
                oos.writeObject(new AuthenticationFailed("Authentification failed"));
            }
        }
        else if(request instanceof CreateAccountRequest)
        {
            CreateAccountRequest req = (CreateAccountRequest) request;
            if(theServer.getData().users.stream().filter(User -> User.name.equals(req.name)).findFirst().isPresent())
            {
                oos.writeObject(new CreateAccountDenied("this username is already taken"));
            }
            else
            {
                theServer.data.users.add(new User(req.name, req.password));
                oos.writeObject(new CreateAccountSuccess("Welcome in our amazing community, please connect to enjoy."));
                theServer.SaveAllData();
            }
        }
        else if(request instanceof CreateTopicRequest)
        {
            CreateTopicRequest req = (CreateTopicRequest)request;
            if(theServer.getData().topics.stream().filter(Topic -> Topic.name.equals(req.name)).findFirst().isPresent())
            {
                oos.writeObject(new CreateTopicDenied("This topic name is already taken."));
            }
            else
            {
                theServer.data.topics.add(new Topic(req.name));
                oos.writeObject(new CreateTopicSuccessed("Topic called " + req.name + " has been successfully created!"));
                theServer.SaveAllData();
            }
        }
        else if(request instanceof FindTopicRequest)
        {
            FindTopicRequest req = (FindTopicRequest)request;
            if(theServer.getData().topics.stream().filter(Topic -> Topic.name.equals(req.name)).findFirst().isPresent())
            {
                Topic foundTopic = theServer.getData().topics.stream().filter(Topic -> Topic.name.equals(req.name)).findFirst().orElse(null);
                oos.writeObject(new FindTopicSuccessed(copyTopic(foundTopic)));
                theServer.SaveAllData();
            }
            else
            {
                oos.writeObject(new FindTopicFailed("the topic you are looking for doesn't exist"));
            }
        }
        else if(request instanceof AddContentToTopicRequest)
        {
            AddContentToTopicRequest req = (AddContentToTopicRequest)request;
            Message content = req.content;
            Topic topic = req.topic;
            theServer.data.topics.get(theServer.data.topics.indexOf(theServer.data.topics.stream().filter(Topic -> Topic.name.equals(topic.name)).findFirst().orElse(null))).messages.add(content);
            Topic test = theServer.data.topics.stream().filter(Topic -> Topic.name.equals(topic.name)).findFirst().orElse(null);
            theServer.UpdateClients(test);
            //oos.writeObject(new AddContentToTopicSucceded(copyTopic(test)));
            //oos.writeObject(test);
            theServer.SaveAllData();

        }
    }

    public Topic copyTopic(Topic tToCopy)
    {
        Topic toSend = new Topic(tToCopy.name);
        for (Message m :tToCopy.messages) {
            toSend.messages.add(m);
        }
        return toSend;
    }

    public void UpdateTopic(Topic topic) throws IOException {
        Topic topicToSend = theServer.getData().topics.stream().filter(Topic -> Topic.name.equals(topic.name)).findFirst().orElse(null);
        oos.writeObject(new UpdateTopicRequest(copyTopic(topicToSend)));
    }
}
