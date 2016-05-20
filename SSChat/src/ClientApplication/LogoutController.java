package ClientApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
/**
 * Created by sandesh on 5/01/2016.
 */

public class LogoutController extends JFrame implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new LoginController();
        this.dispose();
    }

    public void SendToServer(String userName){
        Socket client = ServerContact.connect();
        try {
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF("Logout");
            out.writeUTF(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
