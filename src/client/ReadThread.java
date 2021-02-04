package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread{
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;

    public ReadThread(Socket socket, ChatClient chatClient) {
        this.socket = socket;
        this.client = chatClient;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                String response = reader.readLine();
                System.out.println("\n"+ response);

                if(client.getUserName() != null){
                    System.out.println("[ " + client.getUserName() + " ] : ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
