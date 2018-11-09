package server;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Server myServer = new Server();
        myServer.RegardeClient();
    }
}
