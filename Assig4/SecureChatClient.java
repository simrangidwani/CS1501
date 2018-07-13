//Simran Gidwani
//CS 1501 Summer 2018
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.math.*;
import java.awt.*;
/**
 *
 * @author simrangidwani
 */
public class SecureChatClient extends JFrame implements Runnable, ActionListener{
    public static final int PORT = 8756;
    
    BufferedReader myReader;
    PrintWriter myWriter;
    JTextArea outputArea;
    JLabel prompt;
    JTextField inputField;
    String userName, serverName;
    Socket connection;   
    ObjectOutputStream output;
    ObjectInputStream input;
    private BigInteger E, D, N;
    SymCipher cipher;
    
    public SecureChatClient() {
    
    try{
        userName = JOptionPane.showInputDialog(this, "Enter user name: ");
        serverName = JOptionPane.showInputDialog(this, "Enter the server name: ");
        InetAddress addr = InetAddress.getByName(serverName);
        connection = new Socket(addr, PORT);
        System.out.println("XXXX");
        
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        
        input = new ObjectInputStream(connection.getInputStream());
        
        E = (BigInteger)input.readObject();
        System.out.println("E: " + E);
        
        N = (BigInteger)input.readObject();
        System.out.println("N: " + N);
        
        String addSub = (String)input.readObject();
        System.out.println("Type of symmetric enryption: " + addSub);
        
        if (addSub.equals("Add"))
        {
            cipher = new Add128();
        }
        else
        {
            cipher = new Substitute();
        }
        
        //get keys
        byte [] key = cipher.getKey();
        System.out.println("Symmetric Key: ");
        for (int i= 0; i < key.length; i++)
        {
            System.out.println(key[i] + " ");
        }
        
        BigInteger bigIntKey = new BigInteger(1, key);
        
        BigInteger encrypt = bigIntKey.modPow(E, N);
        //sends key to server
        output.writeObject(encrypt);
        //encodes user name and sends to server
        output.write(cipher.encode(userName));
        //done encoding
        
        this.setTitle(userName);
        Box b = Box.createHorizontalBox();
        outputArea = new JTextArea(8,30);
        outputArea.setEditable(false);
        b.add(new JScrollPane(outputArea));
        
        outputArea.append("Welcome to the Chat Group, " + userName + "\n");
        
        inputField = new JTextField("");
        inputField.addActionListener(this);
        
        prompt = new JLabel("Type your messages below:");
        Container c = getContentPane();
        
        c.add(b, BorderLayout.NORTH);
        c.add(prompt, BorderLayout.CENTER);
        c.add(inputField, BorderLayout.SOUTH);
        
        Thread outputThread = new Thread(this);
        outputThread.start();
        
        addWindowListener(
        new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                try{
                    output.writeObject(cipher.encode("ClIENT CLOSING"));
                }
                catch(Exception e2)
                {
                    System.out.println("Closing failed");
                }
            System.exit(0);
            }
        }
        );
       
        setSize(500, 200);
        setVisible(true);
    }
    
    catch(Exception e)
    {
        System.out.println("Problem starting client!");
    }
    }
    
    public void run()
    {
        while (true)
        {
            try
            {
                //decode msg
                byte [] currMsg = (byte[])input.readObject();
                outputArea.append(cipher.decode(currMsg) + "\n");
            }
            catch(Exception e)
            {
                System.out.println(e + " ,closing client!");
                break;
            }
        }
        System.exit(0);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String currMsg = e.getActionCommand();
        inputField.setText("");
        String msg = userName + ":" + currMsg;
        try
        {
            output.writeObject(cipher.encode(msg));
            
        }
        catch(Exception e3)
        {
            System.out.println("Message Encryption Failed");
        }
        
    }
    
    public static void main(String [] args)
    {
        SecureChatClient JR = new SecureChatClient();
        JR.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    
    
    
    
}
