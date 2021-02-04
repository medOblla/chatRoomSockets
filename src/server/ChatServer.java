package server;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private int port;
    private Set<UserThread> userThreads;

    ChatServer(int port){
        this.port = port;
        this.userThreads = new HashSet<>();
    }

    private void listen(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);
            while(true){
                Socket socket = serverSocket.accept();
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer(8000);
        server.listen();
    }

    void sendToAll(String message, UserThread sender){
        for(UserThread user : userThreads){
            if(user != sender){
                user.sendMessage(message);
            }
        }
    }

    void removeUser(String userName, UserThread userThread){
        if(userThreads.remove(userThread)){
            System.out.println(userName + " quit the conversation");
        }
    }
}
