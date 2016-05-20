package ServerApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Created by sandesh on 5/01/2016.
 */

public class DbConnection {

    private String driver="com.mysql.jdbc.Driver";
    private Connection connection;

    public DbConnection(){

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chatapplication", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection(){
        return connection;
    }



}
