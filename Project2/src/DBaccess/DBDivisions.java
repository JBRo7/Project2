package DBaccess;

import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBConnection;
import model.Customers;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Justin Brown
 */

/** This is used to load the divisions Database. */
public class DBDivisions {

    /** This is selects all the divisions. */
    public static void selectDivision() {
        ObservableList<FirstLevelDivisions> divList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * From first_level_divisions";

            PreparedStatement fld = JDBConnection.getConnection().prepareStatement(sql);


            ResultSet resultSet = fld.executeQuery();
            while(resultSet.next()){
                int division_ID = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
                FirstLevelDivisions div = new FirstLevelDivisions(division_ID, division, countryId);
                ListProvider.addDivisions(div);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /** This is selects all the divisions by ID.
     * @param id gets the divison by id.
     * @return divisions by the Id. */
    public static Customers getDivisionById(int id) {
        Customers divis = null;
        try{
            String sql = "SELECT * From customers WHERE Customer_ID = ?, Division_ID = ?, Division = ?";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);
            p.setInt(1,id);
            p.setInt(2,id);
            p.setString(3, divis.getDivision());

            ResultSet r = p.executeQuery();
            while(r.next()){
                int customerId = r.getInt("Customer_ID");
                int divisionId = r.getInt("Division_ID");
                String division = r.getString("Division");
                divis = new Customers(customerId, divisionId, division);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divis;

    }

}
