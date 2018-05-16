import java.util.*;
import java.io.*;
import java.net.*;

/**
 * @author Jonathon Brandt on 8/28/17.
 * @project QuickChatbox
 */
public class Server {
    static ArrayList<Socket> socketList = new ArrayList<>();

    public static void main(String[] args){
        ServerSocket serverSocket;
        Socket clientSocket;
        ClientListener listener;
        try{
            serverSocket = new ServerSocket(41217);
            while(true){
                clientSocket = serverSocket.accept();
                listener = new ClientListener(clientSocket);
                listener.start();
            }
        }
        catch(IOException ex){
            System.exit(0);
        }
    }

    public static class ClientListener extends Thread{
        Socket socket;

        public ClientListener(Socket _socket){
            socket = _socket;
            socketList.add(socket);
        }

        public void run(){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = null;
                while(true){
                    if((line = reader.readLine()) != null){
                        sendMessage(line);
                    }
                }
            }
            catch(IOException ex){
                System.exit(0);
            }
        }

        public void sendMessage(String _message){
            for(int i = 0; i < socketList.size(); i++){
                if(socketList.get(i) != socket) {
                    try {
                        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socketList.get(i).getOutputStream()));
                        writer.println(_message);
                        writer.flush();
                    } catch (IOException ex) {
                        System.exit(0);
                    }
                }
            }
        }
    }
}
