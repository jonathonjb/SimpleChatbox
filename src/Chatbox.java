import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;

/**
 * @author Jonathon Brandt on 8/28/17.
 * @project QuickChatbox
 */
public class Chatbox extends JFrame {
    JTextArea whiteBoard;
    JTextField commentBox;
    JButton submit;

    ServerListener serverListener;
    Socket socket;

    public Chatbox(){
        serverListener = new ServerListener(this);

        setLocation(200, 100);
        setLayout(new BorderLayout());

        JPanel panelOne = new JPanel();
        whiteBoard = new JTextArea(20, 50);
        panelOne.add(whiteBoard);
        add(panelOne, BorderLayout.NORTH);

        JPanel panelTwo = new JPanel();
        commentBox = new JTextField(40);
        submit = new JButton("Submit");
        panelTwo.add(commentBox);
        panelTwo.add(submit);
        add(panelTwo);
        submit.addActionListener(new SubmitListener());

        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public class SubmitListener implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            if(ae.getSource() == submit){
                String message = commentBox.getText();

                whiteBoard.append(message + "\n");
                sendMessage(message);

                commentBox.setText("");
            }
        }
    }

    public void sendMessage(String _message){
        try{
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.println(_message);
            writer.flush();
        }
        catch (IOException ex){
            System.exit(0);
        }
    }

    public static void main(String[] args){
        new Chatbox();
    }
}
