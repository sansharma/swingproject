package ClientApplication;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
/**
 * Created by sandesh on 5/02/2016.
 */

public class ConversationController extends JFrame implements ListSelectionListener,ActionListener{
    MessageController messageController;
    FriendController friendController;
    LogoutController logoutController;
    JTextArea txtMessage;
    JTextField txtMsg;
    JButton msgSendButton;
    JButton usrLogoutButton;
    JButton addFriend;
    JScrollPane jspSendMessagePane;
    JScrollPane jspTextMessage;
    JList<String> friendNameList;
    private java.util.List<String> friendUsernameList;
    Container container;
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    String yourUsername;
    String friendsUsername;
    JPanel panel = new JPanel();
    DefaultListModel<String> listModel;
    ArrayList<String> allMessageList = null;

    public ConversationController(String yourUsername,String friendsUsername){
        this.yourUsername=yourUsername;
        this.friendsUsername=friendsUsername;

        friendController = new FriendController();
        ArrayList<String> incomingData = friendController.fetchFriend(yourUsername);
        friendUsernameList = new ArrayList<String>();
        listModel = new DefaultListModel<String>();
        for (String info : incomingData) {
            String parseInfo[] = info.split(":");
            listModel.addElement(parseInfo[0]);
            friendUsernameList.add(parseInfo[1]);
        }

        txtMessage = new JTextArea(25, 25);
        messageController = new MessageController();
        allMessageList=messageController.fetchMessageFromServer(yourUsername, friendsUsername);
        if (allMessageList != null) {
            for (String eachMessage : allMessageList) {
                txtMessage.append(eachMessage + "\n");
            }
        } else {
            txtMessage.append("No Conversation Found!");
        }

    }

    Timer t = new Timer(5000,new TimerAction());

    class TimerAction implements ActionListener{

        //Get Data from Database in every 5000 millisecond
        @Override
        public void actionPerformed(ActionEvent e) {
            updateFriendList();
            updateMessage();
        }

        public void updateFriendList(){

            listModel.clear();
            friendUsernameList.clear();
            friendController = new FriendController();
            ArrayList<String> incomingData = friendController.fetchFriend(yourUsername);
            for (String info : incomingData) {
                String parseInfo[] = info.split(":");
                listModel.addElement(parseInfo[0]);
                friendUsernameList.add(parseInfo[1]);
            }
        }

        public void updateMessage(){
            messageController = new MessageController();
            allMessageList = messageController.fetchMessageFromServer(yourUsername, friendsUsername);
            txtMessage.setText("");
            if(allMessageList.size()>0) {
                for (String eachMessage : allMessageList) {
                    txtMessage.append(eachMessage + "\n");
                }
            }else{
                txtMessage.append("No Conversation Found!");
            }
        }
    }

    public void run() {
        this.setTitle("Deerwalk Chat: " + yourUsername + "->" + friendsUsername);
        panel.setLayout(new GridBagLayout());

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth=10;
        gridBagConstraints.gridheight=1;
        gridBagConstraints.weightx=1.0;
        gridBagConstraints.weighty=1.0;
        gridBagConstraints.insets= new Insets(10,10,0,0);
        //gridBagConstraints.fill=GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor=GridBagConstraints.NORTHWEST;
        JButton homeButton = new JButton("Home");
        addFriend = new JButton("Add Friend");
        addFriend.addActionListener(this);
        usrLogoutButton = new JButton("Logout");
        usrLogoutButton.addActionListener(this);
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 2));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.add(addFriend);
        homeButtonPanel.add(usrLogoutButton);
        panel.add(homeButtonPanel, gridBagConstraints);

        gridBagConstraints.gridx=2;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth=1;
        gridBagConstraints.gridheight=1;
        gridBagConstraints.weightx=1.0;
        gridBagConstraints.weighty=1.0;
        gridBagConstraints.anchor=GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets= new Insets(12,0,0,0);
        if(listModel.size()==0){
            listModel.addElement("No Friend List");
        }
        friendNameList = new JList<String>(listModel);
        friendNameList.addListSelectionListener(this);
        friendNameList.setBackground(new Color(255, 255, 255));
        friendNameList.setFixedCellHeight(20);
        friendNameList.setBorder(BorderFactory.createEmptyBorder());
        JPanel listPanel = new JPanel();
        JScrollPane listPane = new JScrollPane(friendNameList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setBorder(BorderFactory.createEmptyBorder());
        listPanel.add(listPane);
        listPanel.setLayout(new GridLayout(3, 1));
        listPanel.setBorder(BorderFactory.createTitledBorder("Friend List"));
        listPanel.setBackground(new Color(255, 255, 255));
        panel.add(listPanel, gridBagConstraints);


        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.anchor=GridBagConstraints.NORTHWEST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(50,12,0,0);
        txtMessage.setEditable(false);
        jspTextMessage = new JScrollPane(txtMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel messageListPanel = new JPanel();
        messageListPanel.setLayout(new GridLayout());
        messageListPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(50, 50, 150)), "Message"));
        messageListPanel.add(jspTextMessage);
        panel.add(messageListPanel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.anchor=GridBagConstraints.NORTHWEST;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.insets=new Insets(500,12,0,0);
        txtMsg = new JTextField(35);

        jspSendMessagePane = new JScrollPane(txtMsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new GridLayout());
        sendMessagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(50, 150, 100)), "Send Message"));
        sendMessagePanel.add(jspSendMessagePane);
        panel.add(sendMessagePanel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets=new Insets(550,12,0,0);
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        msgSendButton = new JButton("Send");
        msgSendButton.addActionListener(this);
        panel.add(msgSendButton, gridBagConstraints);
        container = getContentPane();
        container.add(panel);
        setVisible(true);
        setSize(1300, 800);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new LogoutController().SendToServer(yourUsername);
                System.exit(1);
            }
        });
        t.start();

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            String fetchMessageUser = friendUsernameList.get(friendNameList.getSelectedIndex());
            ConversationController conversationController = new ConversationController(yourUsername, fetchMessageUser);
            conversationController.run();
            this.dispose();
        }catch (Exception ec){
        }
    }

    void notSend(){
        JOptionPane.showMessageDialog(this,"Message sent Failed!","Error",JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton incomingRequest = (JButton)e.getSource();

        if(incomingRequest.equals(msgSendButton)) {
            messageController = new MessageController();
            String message = txtMsg.getText();
            String resultMessage = messageController.sendMessage(yourUsername, friendsUsername, message);
            if (resultMessage.equals("SaveFailed")) {
                notSend();
            } else {
                txtMsg.setText("");
            }
        }else if(incomingRequest.equals(usrLogoutButton)){
            t.stop();
            logoutController = new LogoutController();
            logoutController.SendToServer(yourUsername);
            new LoginController();
            this.dispose();
        }
    }
}
