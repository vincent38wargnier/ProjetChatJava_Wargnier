package client;

import share.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;



public class Client {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ServerListener myListener;
    int state = 0;
    boolean waitForAnswer = false;
    boolean writting = false;
    Topic currentTopic;
    String nameUser = "";
    Scanner sc;

    public Client() throws IOException, ClassNotFoundException, InterruptedException {
        this.sc = new Scanner(System.in);
        socket = new Socket((String)null, 1850);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        //this.Read();
        myListener = new ServerListener(this, ois);
        myListener.start();
        //this.Send();
        this.DoEverything();
    }

    public void DoEverything() throws IOException, InterruptedException {
        //System.out.println("press a key to start");
        //Scanner sc;
        //sc = new Scanner(System.in);
        String key = sc.nextLine();
        if(!key.equals("hack"))
        {
            while(true)
            {
                if(!waitForAnswer)
                {
                    switch (state)
                    {
                        case 0:
                            this.Authenticate();
                            break;
                        case 1:
                            System.out.println("Write 'open' to get into a topic \nWrite 'new' to create a new topic");
                            sc = new Scanner(System.in);
                            Thread.sleep(10);
                            String choice = sc.nextLine();
                            if(choice.equals("open"))
                            {
                                System.out.println("write the name of the topic you are looking for");
                                //sc = new Scanner(System.in);
                                String name = sc.nextLine();
                                this.Send(new FindTopicRequest(name));

                            }
                            else if(choice.equals("new"))
                            {
                                System.out.println("Write the name of the topic you want to create");
                                //sc = new Scanner(System.in);
                                String name = sc.nextLine();
                                this.Send(new CreateTopicRequest(name));
                            }
                            break;
                        case 2:
                            //System.out.println("Write 'add' to add a content to this topic \nWrite 'ex' to leave this topic");
                            //sc = new Scanner(System.in);
                            String choice2 = sc.next();
                            if(choice2.equals("add"))
                            {
                                writting = true;
                                System.out.print("write your message :");
                                Thread.sleep(100);
                                sc = new Scanner(System.in);
                                Thread.sleep(100);
                                String content = sc.nextLine();
                                writting = false;
                                Message message = new Message(content, this.nameUser);
                                this.Send(new AddContentToTopicRequest(message, this.currentTopic ));
                            }
                            else if(choice2.equals("ex"))
                            {
                                currentTopic = null;
                                this.state--;
                            }
                            break;
                    }
                }
                //sc.reset();
                //sc = null;
                Thread.sleep(10);
                //sc.nextLine();
            }
        }
        else
        {
            //oos.writeObject(new FindTopicRequest("test"));

            oos.writeObject(new AddContentToTopicRequest(new Message("salutbatard", "unencule"), new Topic("test")));
        }

    }

    public void Send(Object Request) throws IOException {
        oos.writeObject(Request);
        waitForAnswer = true;
    }


    public void Authenticate() throws IOException {
        System.out.println("write 'co' to sign in or 'new' to sign up");
        //Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine();
        if(choice.equals("co"))
        {
            System.out.println("Enter your name :");
            //sc = new Scanner(System.in);
            String name = sc.nextLine();
            System.out.println("Enter your password");
            String password = sc.nextLine();
            AuthenticationRequest authReq = new AuthenticationRequest(name, password);
            this.Send(authReq);
        }
        else if(choice.equals("new"))
        {
            System.out.println("we will create your account now:");
            System.out.println("Enter your name :");
            //sc = new Scanner(System.in);
            String name = sc.nextLine();
            String password, password2;
            while(true)
            {
                System.out.println("Enter your password");
                //sc = new Scanner(System.in);
                password = sc.next();
                System.out.println("Comfirm your password");
                sc = new Scanner(System.in);
                password2 = sc.nextLine();
                if(password.equals(password2))
                {
                    break;
                }
                else
                {
                    System.out.println("you entered 2 different passwords");
                }
            }
            CreateAccountRequest Req = new CreateAccountRequest(name, password);
            this.Send(Req);
            //oos.writeObject(Req);
        }

    }

    public void ReportAMessage(Object response) throws IOException {
        //System.out.println("response : " + response.getClass());
        if(response instanceof String)
        {
            printColored((String) response, "BLUE");
        }
        else if(response instanceof AuthenticationSuccessed)
        {
            printColored("Authentification successed", "BLUE");
            printColored("Existing topics are the ones following :", "BLUE");
            this.nameUser = ((AuthenticationSuccessed)response).nameUser;
            for (String tName:((AuthenticationSuccessed)response).topics) {
                printColored("   -" + tName, "GREEN");
            }
            this.state++;
        }
        else if(response instanceof AuthenticationFailed)
        {
            this.nameUser = "";
            this.printColored(((AuthenticationFailed)response).message, "RED");
        }
        else if(response instanceof CreateAccountDenied)
        {
            this.nameUser = "";
            this.printColored(((CreateAccountDenied)response).Message, "RED");
        }
        else if(response instanceof CreateAccountSuccess)
        {
            printColored(((CreateAccountSuccess)response).message, "BLUE");
        }
        else if(response instanceof CreateTopicSuccessed)
        {
            printColored(((CreateTopicSuccessed)response).message, "BLUE");
        }
        else if(response instanceof CreateTopicDenied)
        {
            printColored(((CreateTopicDenied)response).message, "RED");
        }
        else if(response instanceof FindTopicSuccessed)
        {
            FindTopicSuccessed resp = (FindTopicSuccessed) response;
            this.currentTopic = resp.topic;
            this.state++;
            PrintTopic(this.currentTopic);
            System.out.println("Write 'add' to add a content to this topic \nWrite 'ex' to leave this topic");
        }
        else if(response instanceof FindTopicFailed)
        {
            FindTopicFailed resp = (FindTopicFailed) response;
            printColored(((FindTopicFailed)resp).message, "RED");
        }
        else if(response instanceof AddContentToTopicSucceded)
        {
            AddContentToTopicSucceded resp = (AddContentToTopicSucceded) response;
            //this.currentTopic = resp.newTopic;
            printColored(resp.message, "BLUE");
            PrintTopic(resp.newTopic);
        }
        else if(response instanceof UpdateTopicRequest)
        {
            if(currentTopic.name == ((UpdateTopicRequest)response).topic.name && state != 0 && writting == false)
            {
                this.currentTopic = ((UpdateTopicRequest)response).topic;
                this.PrintTopic(this.currentTopic);
                System.out.println("Write 'add' to add a content to this topic \nWrite 'ex' to leave this topic");
            }
        }
        waitForAnswer = false;
    }

    void printColored(String myText, String color)
    {
        switch(color)
        {
            case "RED":
                System.out.println("\033[31m" + myText + "\033[0m");
                break;
            case "GREEN":
                System.out.println("\033[32m" + myText + "\033[0m");
                break;
            case "BLUE":
                System.out.println("\033[34m" + myText + "\033[0m");
                break;
            case "YELLOW":
                System.out.println("\033[33m" + myText + "\033[0m");
                break;
        }
    }

    public void PrintTopic(Topic toShow)
    {
        printColored("you are consulting the topic called :\n[   " + currentTopic.name + "   ]", "BLUE");
        if(toShow.messages != null)
        {
            for (Message m: toShow.messages) {
                System.out.println("     -" + m.content + " ~~written by [" + "\033[31m" + m.autor + "\033[0m" + "]~~");
            }
        }
        else
        {
            System.out.println("no one has added content to this topic yet. Be the first!");
        }
    }
}
