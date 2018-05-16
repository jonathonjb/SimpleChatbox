import java.io.*;
import java.net.*;
import java.util.List;

/**
 * @author Jonathon Brandt on 8/28/17.
 * @project QuickChatbox
 */
public class ServerListener {
    Chatbox chatbox;
    String ipAddress = "localhost";
    int portNumber = 41217;

    public ServerListener(Chatbox _chatbox){
        chatbox = _chatbox;
        setUpConnection(ipAddress, portNumber);
        Listener listener = new Listener();
        listener.start();
    }

    public void setUpConnection(String _ipAddress, int _portNumber){
        try {
            chatbox.socket = new Socket(_ipAddress, _portNumber);
        }
        catch(IOException ex){
            System.exit(0);
        }
    }

    public class Listener extends Thread{
        public void run(){
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(chatbox.socket.getInputStream()));
                String line = null;
                while(true){
                    if((line = reader.readLine()) != null){
                        chatbox.whiteBoard.append(line + "\n");
                    }
                }
            }
            catch(IOException ex){
                System.exit(0);
            }
        }
    }
}
