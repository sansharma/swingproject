package ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * Created by sandesh on 5/01/2016.
 */

public class LoginController extends JFrame implements ActionListener {

    JLabel labeluserName;
    JLabel labelpassword;
    JTextField textuserName;
    JPasswordField textpassword;
    JButton loginButton;
    JButton registerButton;
    JPanel loginPanel;
    GridBagConstraints gridBagConstraints;

    String userName;
    char password[];
    String strPassword;



    public LoginController(){
        labeluserName = new JLabel("Enter the Username");
        labelpassword = new JLabel("Enter the password");

        textuserName = new JTextField(20);
        textpassword = new JPasswordField(20);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        loginPanel = new JPanel();
        loginButton.setLayout(new GridLayout());
        gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        loginPanel.add(labeluserName,gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        loginPanel.add(textuserName,gridBagConstraints);

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        loginPanel.add(labelpassword,gridBagConstraints);


        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        loginPanel.add(textpassword,gridBagConstraints);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,5,0));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gridBagConstraints.gridx=2;
        gridBagConstraints.gridy=2;
        gridBagConstraints.insets=new Insets(0,0,0,0);
        loginPanel.add(buttonPanel,gridBagConstraints);

        Container container = getContentPane();
        container.add(loginPanel);

        setVisible(true);
        setSize(450,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void showdlg(){
        JOptionPane.showMessageDialog(this,"Invalid Username or Password!","Message",JOptionPane.ERROR_MESSAGE);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button =(JButton)e.getSource();
        if(button.equals(registerButton)){
            new Register();
            this.dispose();
        }
        else{

            try {
                Socket client = ServerContact.connect();
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);
                out.writeUTF("LoginInfo");
                userName = textuserName.getText();
                password = textpassword.getPassword();
                strPassword = new String(password);
                out.writeUTF(userName+":"+strPassword);
                DataInputStream in = new DataInputStream(client.getInputStream());
                String messageFromServer=in.readUTF();
                if(messageFromServer.equals("Welcome")){
                    new ChatController(userName).displayFirst();
                    this.dispose();
                }else{
                    showdlg();
                }

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        new LoginController();
    }
}
