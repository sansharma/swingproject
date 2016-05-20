package ServerApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by sandesh on 5/07/2016.
 */
public class ServerController {
    private ResultSet resultSet;
    private Connection connection;
    private Statement statement;

    public ServerController(){
        connection = new DbConnection().getConnection();

    }

    //Validating LoginController
    public String validateLogin(String loginInfo){

        String parseInfo[] = loginInfo.split(":");

        String query ="select *from user where Username = '"+parseInfo[0]+"' and Password='"+parseInfo[1]+"'";
        try {
            statement = createStatement();
            resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                String changeStatus = "UPDATE user set status=1 where Id='"+resultSet.getInt("Id")+"'";
                statement.execute(changeStatus);
                return "Welcome";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NotWelcome";
    }

    public String registerNew(String registerInfo){

        String parseInfo[]=registerInfo.split(":");
        if(checkUserName(parseInfo[0])){
            return "UserExists";
        }else{
            String registerUserQuery = "INSERT INTO user VALUES (NULL ,'"+parseInfo[0]+"','"+parseInfo[1]+"',0)";
            try {
                statement=createStatement();
                statement.execute(registerUserQuery);
                int userId=getUserId(parseInfo[0]);
                String registerUserInfoQuery="INSERT INTO userInfo VALUES (NULL ,'"+parseInfo[2]+"','"+parseInfo[3]+"','"+parseInfo[4]+"','"+parseInfo[5]+"','"+parseInfo[6]+"','"+userId+"')";
                statement.execute(registerUserInfoQuery);
                return "Registerd";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "RegistrationFailed";
    }

    public Statement createStatement(){
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public boolean checkUserName(String username){
        String checkUserQuery="select *from user where Username='"+username+"'";
        try {
            statement=createStatement();
            resultSet=statement.executeQuery(checkUserQuery);
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;


    }

    public int getUserId(String username){
        int userId=0;
        String query = "select Id from user where Username = '"+username+"'";
        statement=createStatement();
        try {
            resultSet=statement.executeQuery(query);
                while (resultSet.next()) {
                    userId = resultSet.getInt("Id");
                }
                return userId;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }


    public ArrayList<String> fetchFriendList(String userName){
        int userId = getUserId(userName);
        ArrayList<String> listFriend = new ArrayList<String>();
        String query = "select *from friends where UserId1="+userId+"";
        List<Integer> listId = new ArrayList<Integer>();
        statement=createStatement();
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int fetchedId=resultSet.getInt("UserId2");
                listId.add(fetchedId);
            }
            List<String> online = new ArrayList<String>();
            List<String> offline = new ArrayList<String>();
            for(int eachId:listId){
                String fetchedUserName = fetchUsername(eachId);
                String fetchedName = fetchUserInfo(eachId);
                int status = fetchStatus(eachId);
                if(status == 1){
                    online.add(fetchedName+"(online):"+fetchedUserName);
                }else{
                    offline.add(fetchedName+"(offline):"+fetchedUserName);
                }

            }
            listFriend.addAll(online);
            listFriend.addAll(offline);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listFriend;
    }


    public int fetchStatus(int userId){
        String query ="select status from user where Id='"+userId+"'";
        statement = createStatement();
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                return resultSet.getInt("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public String fetchUsername(int userId){

        String username="";
        String query = "select Username from user where Id = '"+userId+"'";
        statement=createStatement();
        try {
            resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                username = resultSet.getString("Username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;

    }

    public String fetchUserInfo(int userId){
        String name="";
        String query = "select FirstName,LastName from userInfo where UserId = '"+userId+"'";
        statement=createStatement();
        try {
            resultSet=statement.executeQuery(query);
            while (resultSet.next()){
                name = resultSet.getString("FirstName")+" "+resultSet.getString("LastName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name;
    }

    public ArrayList<String> fetchAllMessages(String bothUsername){
        String parseUsername[]=bothUsername.split(":");
        int user1 = getUserId(parseUsername[0]);
        int user2 = getUserId(parseUsername[1]);
        ArrayList<String> conversationsList = new ArrayList<String>();
        String current= fetchUserInfo(user1);
        String fetchingUser = fetchUserInfo(user2);

        String query = "select *from messages where (SenderId='"+user1+"' and ReceiverId='"+user2+"') or (SenderId='"+user2+"' and ReceiverId='"+user1+"')";
        statement = createStatement();

        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int senderId = resultSet.getInt("SenderId");

                if(senderId==user1){
                    conversationsList.add(current+":"+resultSet.getString("Message"));
                }else{
                    conversationsList.add(fetchingUser+":"+resultSet.getString("Message"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conversationsList;
    }

    public String saveMessage(ArrayList<String> info){
        int senderId = getUserId(info.get(0));
        int receiverId = getUserId(info.get(1));
        String message = info.get(2);

        String query = "INSERT into messages values(NULL,'"+senderId+"','"+receiverId+"','"+message+"')";
        statement = createStatement();

        try {
            statement.execute(query);
            return "SaveSuccess";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "SaveFailed";
    }

    public String AddNewFriend(String info){

        String parseInfo[] = info.split(":");
        int userId1 = getUserId(parseInfo[0]);
        int userId2 = getUserId(parseInfo[1]);
        System.out.println(userId1);
        System.out.println(userId2);
        if(userId2 !=0){
            String query = "INSERT into friends values(NULL,'"+userId1+"','"+userId2+"')";
            try {
                statement.execute(query);
                return "Added";
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return "Failed";
    }

    public void Logout(String userName){
        int userId = getUserId(userName);
        String changeStatus = "UPDATE user set status=0 where Id='"+userId+"'";
        try {
            statement.execute(changeStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
