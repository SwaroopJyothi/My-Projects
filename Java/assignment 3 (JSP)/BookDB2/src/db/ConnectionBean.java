package db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBean implements Serializable {

    Connection connection;
    boolean con = false;

    private String username, password, database_url;

    public ConnectionBean() {

        username = null;

        password = null;



    }

    public String getUsername() {

        return username;

    }

    public void setUsername(String username) {

        this.username = username;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

       public Connection dbConnect() {

        try {

            database_url = ("jdbc:mysql://cs99.bradley.edu:3306/"+username);

            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(database_url,username,password);

            return connection;

        } catch (ClassNotFoundException e) {

            return null;

        } catch (SQLException e) {

            
            return null;
        }

    }

}
