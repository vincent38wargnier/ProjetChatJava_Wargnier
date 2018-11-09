package server;


import client.Client;
import share.Topic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    ServerSocket ss;
    public DataBase data;
    private AutoSaver myReminder;
    List<ClientHandler> myClients = new ArrayList<>();
    public Server() throws IOException, ClassNotFoundException {
         ss = new ServerSocket(1850);
         data = new DataBase();
         //myReminder = new AutoSaver(this,1000);
         //myReminder.start();
         System.out.println("Server ready");
    }

    public void RegardeClient() throws IOException {
        while (true)
        {
            Socket socketCLient = ss.accept();
            System.out.println("un client s'est connecté");
            ClientHandler myCH = new ClientHandler(this, socketCLient);
            myClients.add(myCH);
            myCH.run();
        }
    }

    public DataBase getData() {
        return data;
    }


    //synchronized attend que l'appel de la méthode soit terminé avant de faire un nouvel appel
    public synchronized void SaveAllData() throws IOException {
        this.data.SaveAll();
        //System.out.println("all data has been successfully saved in hardDisc files!");
    }

    public void UpdateClients(Topic topicToUpdate) throws IOException {
        for (ClientHandler ch: myClients) {
            ch.UpdateTopic(topicToUpdate);
        }
    }
}
