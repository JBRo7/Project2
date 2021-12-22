package DBaccess;

import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBConnection;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Justin Brown
 */
/** This is used to load the UsersDatabase. */
public class DBUsers {

    /** This is used to select all the users. */
    public static void selectUsers() {
        ListProvider.getAllUsers().clear();

        try {
            String user = "SELECT * FROM users";

            PreparedStatement usr = JDBConnection.getConnection().prepareStatement(user);

            ResultSet r = usr.executeQuery();
            while (r.next()) {
                //the quotes is the database column names.
                int userId = r.getInt("User_ID");
                String userName = r.getString("User_Name");

                Users U = new Users(userId, userName);
                ListProvider.addUsers(U);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Users getUserById(int id) {
        Users user = null;
        try{
            String sql = "SELECT * From users WHERE User_ID = ?";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);
            p.setInt(1,id);

            ResultSet r = p.executeQuery();
            while(r.next()){
                int userId = r.getInt("User_ID");
                String userName = r.getString("User_Name");
                user = new Users(userId, userName);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;

    }

    /** This gets all users. Displays all users based on userId and userName.
     * @return getAllUsers gets the users and adds to an observable list. */
    //This list is used for collecting userId and userName
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> userList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM users";

            PreparedStatement usr = JDBConnection.getConnection().prepareStatement(sql);

            ResultSet r = usr.executeQuery();
            while (r.next()) {
                int userId = r.getInt("User_ID");
                String userName = r.getString("User_Name");

                Users users = new Users(userId, userName);
                userList.add(users);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return userList;

    }


}
