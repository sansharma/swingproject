package ServerApplication;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
/**
 * Created by sandesh on 5/07/2016.
 */
public class ServerChat extends Thread
{
    private ServerSocket serverSocket;
    ServerController serverController;

    public ServerChat(int port) throws IOException
    {
        serverSocket = new ServerSocket(port);
    }

    public void run()
    {
        while(true)
        {
            try
            {
                Socket server = serverSocket.accept();

                System.out.println("New Request From "
                        + server.getRemoteSocketAddress());

                DataInputStream in =
                        new DataInputStream(server.getInputStream());

                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                String whichAction = in.readUTF();
                serverController = new ServerController();
                if(whichAction.equals("LoginInfo")){
                    String message = serverController.validateLogin(in.readUTF());
                    out.writeUTF(message);
                }else if(whichAction.equals("RegisterInfo")){
                    String resultMessage = serverController.registerNew(in.readUTF());
                    out.writeUTF(resultMessage);
                }else if(whichAction.equals("FriendList")){
                    ObjectOutputStream objout = new ObjectOutputStream(server.getOutputStream());
                    ArrayList<String> friendList;
                    friendList = serverController.fetchFriendList(in.readUTF());
                    objout.writeObject(friendList);
                }else if(whichAction.equals("MessageList")){
                    ObjectOutputStream objout = new ObjectOutputStream(server.getOutputStream());
                    ArrayList<String> messageList;
                    messageList=serverController.fetchAllMessages(in.readUTF());
                    objout.writeObject(messageList);
                }else if(whichAction.equals("SendMessage")){
                    ObjectInputStream inobj = new ObjectInputStream(server.getInputStream());
                    Object obj = inobj.readObject();
                    ArrayList<String> info = (ArrayList<String>)obj;
                    String resultMessage = serverController.saveMessage(info);
                    out.writeUTF(resultMessage);
                }else if(whichAction.equals("AddNewFriend")){
                    System.out.println("Inside the friend add zone");
                    out.writeUTF(serverController.AddNewFriend(in.readUTF()));
                }else if(whichAction.equals("Logout")){
                    serverController.Logout(in.readUTF());
                }
            }catch(SocketTimeoutException s)
            {
                System.out.println("Socket timed out!");
                break;
            }catch(IOException e)
            {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }




    public static void main(String[] args)
    {
        int port = 6003;
        try
        {
            Thread t = new ServerChat(port);
            t.start();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}