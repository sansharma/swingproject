package ClientApplication;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
/**
 * Created by sandesh on 5/02/2016.
 */

public class FriendController {

    public ArrayList<String> fetchFriend(String userName){
        ArrayList<String> friendList=null;
        try {
            Socket client = ServerContact.connect();
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            out.writeUTF("FriendList");
            out.writeUTF(userName);
            ObjectInputStream objin = new ObjectInputStream(client.getInputStream());
            Object obj = objin.readObject();
            friendList = (ArrayList<String>)obj;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return friendList;
    }

    public String AddNewFriend(String yourUsername,String friendUsername){

        Socket client = ServerContact.connect();
        System.out.println(client.getPort());
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("AddNewFriend");
            System.out.println("Connecting");
            out.writeUTF(yourUsername+":"+friendUsername);
            DataInputStream in = new DataInputStream(client.getInputStream());
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Failed";
    }

}
