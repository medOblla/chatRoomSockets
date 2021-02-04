package server;
import java.io.*;
import java.net.*;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    UserThread(Socket socket, ChatServer server){
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            String userName = reader.readLine();

            String clientMessage;

            do{
                clientMessage = reader.readLine();
                String serverMessage = "[ "+ userName + " ] : " + clientMessage;
                server.sendToAll(serverMessage, this);
            }while(!clientMessage.equals("bye"));

            server.removeUser(userName, this);
            socket.close();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }
}
