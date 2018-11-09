package server;

import share.Message;
import share.Topic;
import share.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    public List<User> users = new ArrayList<User>();
    public List<Topic> topics = new ArrayList<Topic>();
    /**
     * The name of the file used to store the data
     */
    private File fileUsers, fileTopics;

    //CONSTRUCTOR
    public DataBase() throws IOException, ClassNotFoundException {
        this.fileUsers = new File("dataUsers.vw");
        this.fileTopics = new File("dataTopics.vw");
        users = this.loadUsers();
        topics = this.loadTopics();
        //this.showDataBaseContent();
    }

    //Load Users from file
    private ArrayList<User> loadUsers() throws IOException, ClassNotFoundException {
        //users.add(new User("test", "test"));
        ArrayList<User> data = new ArrayList<User>();

        // This checks if the file actually exists
        if(this.fileUsers.exists() && !this.fileUsers.isDirectory()) {

            FileInputStream inputS = new FileInputStream(fileUsers);
            ObjectInputStream objectIS = new ObjectInputStream(inputS);
            //System.out.println("Le fichier de sauvegarde existe.");
            try
            {
                while (true)
                {
                    data.add((User)objectIS.readObject());
                }

            }
            catch (Exception e)
            {

            }
            objectIS.close();
            inputS.close();
        } else {
            //System.out.println("Le fichier de sauvegarde n'existe pas.");
            data.add(new User("admin", "admin"));
            this.saveUsers(data);
        }
        //System.out.println(data.size() + " utilisateurs chargés");
        return data;
    }

    //Load TOPICS from file
    private ArrayList<Topic> loadTopics() throws IOException, ClassNotFoundException {
        ArrayList<Topic> data = new ArrayList<Topic>();

        // This checks if the file actually exists
        if(this.fileTopics.exists() && !this.fileTopics.isDirectory()) {

            FileInputStream inputS = new FileInputStream(fileTopics);
            ObjectInputStream objectIS = new ObjectInputStream(inputS);
            try
            {
                while (true)
                {
                    data.add((Topic) objectIS.readObject());
                }

            }
            catch (Exception e)
            {

            }
            objectIS.close();
            inputS.close();
            //System.out.println(data.size() + " Pokémon(s) chargé(s) depuis la sauvegarde.");
        } else {
            //System.out.println("Le fichier de sauvegarde n'existe pas.");
            data.add(new Topic("test"));
            this.saveTopics(data);
        }
        return data;
    }

    //Save USERS
    private void saveUsers(ArrayList<User> data) throws IOException {

        FileOutputStream outputS = new FileOutputStream(fileUsers);
        ObjectOutputStream objectOS = new ObjectOutputStream(outputS);
        for (User d : data) {
            objectOS.writeObject(d);
        }
        objectOS.close();
        outputS.close();


        //System.out.println("Sauvegarde effectuée... " + data.size() + " users(s) en banque.");
    }


    //Save TOPICS
    private void saveTopics (ArrayList<Topic> data) throws IOException {

        FileOutputStream outputS = new FileOutputStream(fileTopics);
        ObjectOutputStream objectOS = new ObjectOutputStream(outputS);
        for (Topic d : data) {
            objectOS.writeObject(d);
        }
        objectOS.close();
        outputS.close();
    }

    private void showDataBaseContent()
    {
        System.out.println("database content :");
        System.out.println("users :");
        for (User u : users) {
            System.out.println("name : " + u.name + ", password : " + u.password);
        }
        System.out.println("topics :");
        for (Topic t : topics) {
            System.out.println("name : " + t.name);
            for (Message m: t.messages) {
                System.out.println("    -" + m.content + ", written by : " + m.autor);
            }
        }
    }

    public void SaveAll() throws IOException {
        this.saveUsers((ArrayList<User>) this.users);
        this.saveTopics((ArrayList<Topic>) this.topics);
        //this.showDataBaseContent();
    }

}
