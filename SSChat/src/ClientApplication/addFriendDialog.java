package ClientApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by l on 5/12/2016.
 */
public class addFriendDialog extends JFrame implements ActionListener {
    private JTextField addFriendText;
    private JLabel addFriendLabel;
    private JButton addAsFriendButton;
    private FriendController friendController;
    private String username;
    public addFriendDialog(String uname){
        this.username = uname;
        JPanel panel = new JPanel();
        addFriendText = new JTextField(40);
        addFriendLabel = new JLabel("Enter the Username");
        addAsFriendButton = new JButton("Add Friend");
        panel.add(addFriendLabel);
        panel.add(addFriendText);
        panel.add(addAsFriendButton);
        addFriendLabel.setBounds(30,10,200,50);
        addFriendText.setBounds(30,60,120,30);
        addAsFriendButton.setBounds(30,110,120,30);
        addAsFriendButton.addActionListener(this);
        panel.setLayout(null);
        setVisible(true);
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        if(button.equals(addAsFriendButton)){
            String frienduName = addFriendText.getText();
            if(frienduName.length()>1){
                System.out.println("Calling Friend Controller");
                friendController = new FriendController();
                String resultMessage = friendController.AddNewFriend(username, frienduName);
                if(resultMessage.equals("Added")){
                    addFriendSuccess();
                }else{
                    noUsernameMatch();
                }
            }

        }
    }
    void addFriendSuccess(){
        JOptionPane.showMessageDialog(this,"Friend Request Sent!","Message",JOptionPane.INFORMATION_MESSAGE);
    }


    void noUsernameMatch(){
        JOptionPane.showMessageDialog(this,"No Any Username match!","Message",JOptionPane.ERROR_MESSAGE);
    }
}
