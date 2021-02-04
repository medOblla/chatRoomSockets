package client;
import java.net.*;
import java.io.*;

public class ChatClient {
    private String hostName;
    private int port;
    private String userName;

    ChatClient(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }

    public void connect(){
        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("Connected to server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 8000);
        client.connect();
    }
}
