package ClientApplication;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Created by sandesh on 5/05/2016.
 */

public class MessageController{

    public ArrayList<String> fetchMessageFromServer(String userName,String fetchMessageUser){
        ArrayList<String> messageList = new ArrayList<String>();
        try {
            Socket client = ServerContact.connect();
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("MessageList");
            out.writeUTF(userName + ":" + fetchMessageUser);
            ObjectInputStream objin = new ObjectInputStream(client.getInputStream());
            Object obj = objin.readObject();
            messageList = (ArrayList<String>)obj;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return messageList;
    }

    public String sendMessage(String sender,String receiver,String message){
        Socket client = ServerContact.connect();
        OutputStream outToServer = null;
        String resultMessage="";
        try {
            outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            DataInputStream in = new DataInputStream(client.getInputStream());
            out.writeUTF("SendMessage");
            ObjectOutputStream objout = new ObjectOutputStream(client.getOutputStream());
            ArrayList<String> info = new ArrayList<String>();
            info.add(sender);
            info.add(receiver);
            info.add(message);
            objout.writeObject(info);
            resultMessage=in.readUTF();
            return resultMessage;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMessage;
    }

}
