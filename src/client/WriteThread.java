package client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WriteThread extends Thread{
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriteThread(Socket socket, ChatClient chatClient) {
        this.socket = socket;
        this.client = chatClient;

        try {
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String userName = console.nextLine();
        client.setUserName(userName);
        writer.println(userName);

        String msg;
        do{
            System.out.print("[ " + userName + " ] : ");
            msg = console.nextLine();
            writer.println(msg);
        }while(!msg.equals("bye"));

        // socket.close()
        // apparement cette instruction ne marche pas, et cela ne laisse pas l'utilisateur sorter,
        // apres que j'ai chercher ce problem j'ai trouver que cela produit un bug do 'closing socket' lorsqu'on travail avec System.in
    }
}
