package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Justin Brown
 */

/** This is used to make the database connection. */
public class JDBConnection {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost:3306/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String username = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    private static Connection connection = null;  // Connection Interface

    /** This starts the connection.
     * @return startConnection is used to start the connection. */
    public static Connection startConnection() {
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, username, password);


        }
        catch (SQLException e){
            //System.out.println(e.getMessage());\
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
        System.out.println("Connection successful!");
        return connection;

    }



    /** This is used to get the connection.
     * @return connection is used to return the connection. */
// get the connection
    public static Connection getConnection(){
        return connection;
    }

    public static void closeConnection() {
        try{
            connection.close();
        }
        catch (Exception e){
        }
    }
}
