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
/**
 * Created by sandesh on 5/05/2016.
 */
public class Register extends JFrame implements ActionListener{
    JLabel labelHeading;
    JLabel labeluserName;
    JLabel labelpassword;
    JLabel labelConfirmpassword;
    JLabel labelstudentId;
    JLabel labelfirstName;
    JLabel labellastName;
    JLabel labelSex;
    JLabel labelAge;

    JTextField textuserName;
    JPasswordField textPassword;
    JPasswordField textconfirmPassword;
    JTextField textstudentId;
    JTextField textfirstName;
    JTextField textlastName;
    JTextField textAge;
    JComboBox textSex;

    JButton submitButton;
    JButton cancelButton;

    String userName;
    char charPassword[];
    char cnfPassword[];
    String studentId;
    String firstName;
    String lastName;
    String age;
    String sex;



    JPanel registerPanel;
    GridBagConstraints gridBagConstraints;


    public Register(){
        this.setTitle("Register");
        registerPanel = new JPanel();
        Color c = new Color(0,200,0);
        registerPanel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        labelHeading = new JLabel("Registration Info");
        labelHeading.setFont(new Font("Monospaced", Font.BOLD, 24));
        labelHeading.setForeground(new Color(131, 25, 38));
        labelHeading.setVerticalAlignment(SwingConstants.TOP);
        gridBagConstraints.anchor=GridBagConstraints.EAST;
        registerPanel.add(labelHeading, gridBagConstraints);

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        labeluserName = new JLabel("Enter Username");
        gridBagConstraints.anchor=GridBagConstraints.WEST;
        registerPanel.add(labeluserName, gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        textuserName = new JTextField(20);
        registerPanel.add(textuserName,gridBagConstraints);

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        labelpassword = new JLabel("Enter Password");
        registerPanel.add(labelpassword,gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=2;
        textPassword = new JPasswordField(20);
        registerPanel.add(textPassword,gridBagConstraints);


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=3;
        labelConfirmpassword = new JLabel("Confirm Password");
        registerPanel.add(labelConfirmpassword,gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=3;
        textconfirmPassword = new JPasswordField(20);
        registerPanel.add(textconfirmPassword,gridBagConstraints);


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=4;
        labelstudentId = new JLabel("Student Id");
        registerPanel.add(labelstudentId,gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=4;
        textstudentId = new JTextField(5);
        registerPanel.add(textstudentId,gridBagConstraints);

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=5;
        labelfirstName = new JLabel("First Name");
        registerPanel.add(labelfirstName,gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=5;
        textfirstName = new JTextField(20);
        registerPanel.add(textfirstName,gridBagConstraints);


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=6;
        labellastName = new JLabel("Last Name");
        registerPanel.add(labellastName,gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=6;
        textlastName = new JTextField(20);
        registerPanel.add(textlastName,gridBagConstraints);


        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=7;
        labelSex = new JLabel("Age");
        registerPanel.add(labelSex,gridBagConstraints);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=7;
        textAge = new JTextField(3);
        registerPanel.add(textAge,gridBagConstraints);

        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=8;
        labelAge = new JLabel("Sex");
        registerPanel.add(labelAge,gridBagConstraints);

        String sexList[]={"Male","Female","Third Party :D"};
        textSex = new JComboBox(sexList);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=8;
        textSex.setSelectedIndex(0);
        registerPanel.add(textSex, gridBagConstraints);


        JPanel buttonPanel = new JPanel();

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        buttonPanel.add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=9;
        registerPanel.add(buttonPanel,gridBagConstraints);


        getContentPane().add(registerPanel);
        setVisible(true);
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        JButton incomingRequest = (JButton)e.getSource();

        if(incomingRequest.equals(submitButton)){

            try {
                userName=textuserName.getText().trim();
                charPassword=textPassword.getPassword();
                String password=new String(charPassword).trim();
                cnfPassword=textconfirmPassword.getPassword();
                String confirmPassword = new String(cnfPassword).trim();

                if(password.equals(confirmPassword)) {
                    studentId = textstudentId.getText().trim();
                    firstName = textfirstName.getText().trim();
                    lastName = textlastName.getText().trim();
                    age = textAge.getText().trim();
                    sex = String.valueOf(textSex.getSelectedItem()).trim();

                    if(userName.length()>1 && password.length()>1 && studentId.length()>1 && firstName.length()>1 &&
                            lastName.length()>1 && age.length()>1 && sex.length()>1){
                        Socket client = ServerContact.connect();
                        OutputStream outToServer = client.getOutputStream();
                        DataOutputStream out = new DataOutputStream(outToServer);
                        DataInputStream in = new DataInputStream(client.getInputStream());
                        out.writeUTF("RegisterInfo");
                        out.writeUTF(userName + ":" + password + ":" + firstName + ":" + lastName + ":" + studentId + ":" + age + ":" + sex);
                        String resultMessage = in.readUTF();

                        if(resultMessage.equals("UserExists")){
                            showUserExists();
                        }else if(resultMessage.equals("RegistrationFailed")){
                            inputError();
                        }else{
                            showSuccessRegister();
                            new LoginController();
                            this.dispose();
                        }
                    }else{
                        inputValueError();
                    }


                }else{
                    passwordError();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }else{
            new LoginController();
            this.dispose();
        }

    }

    void passwordError(){
        JOptionPane.showMessageDialog(this,"Password and Confirm Password did not match","Message",JOptionPane.ERROR_MESSAGE);
    }

    void showSuccessRegister(){
        JOptionPane.showMessageDialog(this,"Registerd Succesfully. Please LoginController.","Success",JOptionPane.INFORMATION_MESSAGE);
    }

    void showUserExists(){
        JOptionPane.showMessageDialog(this,"Registration Failed. Username already exists.","Failed",JOptionPane.ERROR_MESSAGE);
    }

    void inputError(){
        JOptionPane.showMessageDialog(this,"Registration Failed. Internal server error or error in Input parameter.","Error",JOptionPane.ERROR_MESSAGE);
    }

    void inputValueError(){
        JOptionPane.showMessageDialog(this,"All fields are required to be filled!.","Error",JOptionPane.ERROR_MESSAGE);
    }

}
