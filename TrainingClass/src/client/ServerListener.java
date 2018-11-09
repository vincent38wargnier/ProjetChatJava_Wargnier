package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerListener extends Thread{
    private ObjectInputStream ois;
    private Client myDad;
    public ServerListener(Client myDad, ObjectInputStream ois) throws IOException {
        this.myDad = myDad;
        this.ois = ois;
    }



    public void Read() throws IOException, ClassNotFoundException {
        while (true)
        {
            Object response = ois.readObject();
            myDad.ReportAMessage(response);
        }
    }

    @Override
    public void run() {
        try {
            Read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
