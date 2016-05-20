package ClientApplication;

import java.io.IOException;
import java.net.Socket;
/**
 * Created by sandesh on 5/01/2016.
 */

public class ServerContact {
    public static Socket connect(){
        String serverName = "localhost";
        int port = 6003;
        System.out.println("Request sent to Server.");
        Socket client = null;
        try {
            client = new Socket(serverName, port);
            return client;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client;
    }
}
