package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientListener extends Thread{
    private ObjectInputStream ois;
    private ClientHandler myDad;

    public ClientListener(ClientHandler myDad, ObjectInputStream ois) {
        this.ois = ois;
        this.myDad = myDad;
    }

    public void Read() throws IOException, ClassNotFoundException, InterruptedException {
        while (true)
        {
            Object req = ois.readObject();
            myDad.ReportAMessage(req);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
