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
import java.util.List;
/**
 * Created by sandesh on 5/01/2016.
 */

public class ChatController extends JFrame implements ActionListener,ListSelectionListener{

    FriendController friendController;
    LogoutController logoutController;
    public String userName;

    JButton usrLogoutButton;
    JList<String> friendNameList;
    private List<String> friendUsernameList;
    Container container;
    JPanel panel = new JPanel();
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    DefaultListModel<String> listModel;
    ArrayList<String> allMessageList = null;
    JButton addFriend;
    JTextField addFriendText;
    JButton addAsFriendButton;
    JLabel addFriendLabel;


    public ChatController(String uName) {

        this.userName = uName;
        this.setTitle("Deerwalk Chat: " + userName);

        friendController = new FriendController();
        ArrayList<String> incomingData = friendController.fetchFriend(userName);
        friendUsernameList = new ArrayList<String>();
        listModel = new DefaultListModel<String>();
        for (String info : incomingData) {
            String parseInfo[] = info.split(":");
            listModel.addElement(parseInfo[0]);
            friendUsernameList.add(parseInfo[1]);
        }
    }

    Timer t = new Timer(5000,new TimerAction());
    class TimerAction implements ActionListener {
        //Get Data from Database in every 5000 millisecond
        @Override
        public void actionPerformed(ActionEvent e) {
            updateFriendList();
        }

        public void updateFriendList() {

            listModel.clear();
            friendUsernameList.clear();
            friendController = new FriendController();
            ArrayList<String> incomingData = friendController.fetchFriend(userName);
            for (String info : incomingData) {
                String parseInfo[] = info.split(":");
                listModel.addElement(parseInfo[0]);
                friendUsernameList.add(parseInfo[1]);
            }
            if(listModel.size()==0){
                listModel.addElement("No Friend List");
            }
        }
    }


    public void displayFirst(){
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
        gridBagConstraints.insets= new Insets(10,0,0,0);
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
        container = getContentPane();
        container.add(panel);
        setVisible(true);
        setSize(600, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logoutController = new LogoutController();
                logoutController.SendToServer(userName);
                System.exit(1);
            }
        });
        t.start();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        System.out.println("Action Called "+button.getText());
        if(button.equals(usrLogoutButton)){
            t.stop();
            logoutController = new LogoutController();
            logoutController.SendToServer(userName);
            new LoginController();
            this.dispose();
        }else if(button.equals(addFriend)){
           addFriendDialog afdialog = new addFriendDialog(userName);
          // JOptionPane.showInputDialog(this,"Add Friend");

        }

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        try {
            String fetchMessageUser = friendUsernameList.get(friendNameList.getSelectedIndex());
            ConversationController conversationController = new ConversationController(userName, fetchMessageUser);
            conversationController.run();
            this.dispose();
        }catch (Exception er){

        }
    }




}
