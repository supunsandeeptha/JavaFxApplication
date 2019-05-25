package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static String host = "jdbc:mysql://localhost:3306/employees";
    private static String uName = "root";
    private static String password = "root";

    //connection to server
    Connection con;


    {
        try {
            //connection details
            con = DriverManager.getConnection(host, uName, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
