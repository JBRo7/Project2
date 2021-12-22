package DBaccess;

import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBConnection;
import model.Contacts;
import model.Countries;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Justin Brown
 */

/** This is used to select Contacts database. */
public class DBContacts {

    /** This selects all the contacts. */
    public static void selectContacts() {
        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);

            ResultSet r = p.executeQuery();
            while(r.next()){

                //the quotes is the database column names.
                int contactId = r.getInt("Contact_ID");
                String contactName= r.getString("Contact_Name");

                Contacts Cont = new Contacts(contactId, contactName);
                ListProvider.addContacts(Cont);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }


    }




    /** This selects all the contacts by Id.
     * @param id by the id.
     * @return the contactById. */
    public static Contacts getContactById(int id) {
        Contacts cont = null;
        try{
            String sql = "SELECT * From contacts WHERE Contact_ID = ?";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);
            p.setInt(1,id);

            ResultSet r = p.executeQuery();
            while(r.next()){
                int contactId = r.getInt("Contact_ID");
                String contactName = r.getString("Contact_Name");
                cont = new Contacts(contactId, contactName);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cont;

    }

    /** This is selects all the contacts.
     * @return gives an observable list */
    //This list is used for collecting contactId and contactName
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);

            ResultSet r = p.executeQuery();
            while(r.next()){
                int contactId = r.getInt("Contact_ID");
                String contactName= r.getString("Contact_Name");

                Contacts contacts = new Contacts(contactId, contactName);
                contactList.add(contacts);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }
}
