package DBaccess;

import Utils.ListProvider;
import model.Countries;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBConnection;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Justin Brown
 */

/** This is used to select all the countries. */
public class DBCountries {

    /** This selects all the countries. */
    public static void selectCountry() {
        ListProvider.getAllCountries().clear();

        try{
            String sql = "SELECT * FROM countries";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);

            ResultSet r = p.executeQuery();
            while(r.next()){

                //the quotes is the database column names.
                int countryId = r.getInt("Country_ID");
                String country= r.getString("Country");

                Countries C = new Countries(countryId, country);
                ListProvider.addCountry(C);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }


    }


    /** This selects all the countries by Id.
     * @param id by id.
     * @return country by Id*/
        public static Countries getCountryById(int id) {
            Countries count = null;
            try{
                String sql = "SELECT * From countries WHERE Country_ID = ?";

                PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);
                p.setInt(1,id);

                ResultSet r = p.executeQuery();
                while(r.next()){
                    int countryId = r.getInt("Country_ID");
                    String country = r.getString("Country");
                    count = new Countries(countryId, country);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return count;

        }


    /** This is used for all countries observableList.
     * @return the countries. */
    public static ObservableList<Countries> getAllCountries(){
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();

        //adds the table data from mysql to the Customer.fxml table
        try {
            String sql = "SELECT * FROM countries";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);

            ResultSet r = p.executeQuery();
            while(r.next()){
                String country = r.getString("Country");
                Countries countryName = new Countries(country);
                countriesList.add(countryName);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        //return countriesList;
        return null;
    }
    }

