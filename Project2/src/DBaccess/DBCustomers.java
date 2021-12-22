package DBaccess;

import Utils.ListProvider;
import model.Customers;
import main.JDBConnection;

import java.sql.*;

/**
 *
 * @author Justin Brown
 */
/** This is used for selecting the Customers Database. */
public class DBCustomers {

    /** This is selects all the customers. */
    public static void selectCustomer() {
        ListProvider.getAllCustomers().clear();

        try{
            String sql = //"SELECT * FROM customers";
            "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code,"
                    + "customers.Phone, first_level_divisions.Division, customers.Division_ID, first_level_divisions.Country_ID,"
                    + "countries.Country FROM customers, first_level_divisions, countries WHERE customers.Division_ID ="
                    + "first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);

            ResultSet r = p.executeQuery();
            while(r.next()){

                //the quotes are for the database column names.
                int customerId = r.getInt("Customer_ID");
                String customerName= r.getString("Customer_Name");
                String address= r.getString("Address");
                String postalCode= r.getString("Postal_Code");
                String phone= r.getString("Phone");
                int divisionId=r.getInt("Division_ID");
                int countryId=r.getInt("Country_ID");
                String division=r.getString("Division");
                String country= r.getString("Country");
                Customers C = new Customers(customerId, customerName, address, postalCode, phone, divisionId, countryId, division, country);
                ListProvider.addCustomers(C);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }


    }

    /** This is selects all the customers by ID.
     * @param id by the id.
     * @return the customer by id. */
    public static Customers getCustomerById(int id) {
        Customers cust = null;
        try{
            String sql = "SELECT * From customers WHERE Customer_ID = ?";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);
            p.setInt(1,id);

            ResultSet r = p.executeQuery();
            while(r.next()){
                int customerId = r.getInt("Customer_ID");
                String customerName = r.getString("Customer_Name");
                cust = new Customers(customerId, customerName);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cust;

    }

    /** This is used for creating new customers.
     * @param customerName gives the customerName.
     * @param address gives the address.
     * @param postalCode gives the postalCode.
     * @param phone gives the phone number.
     * @param division gives the division. */
    // used for creating a new customer.
public static void createCustomer(String customerName, String address, String postalCode, String phone, String division){
    try{
            //Insert into primary key last. so divisions first and customers should be last.
        String sqlcc = "SELECT * from first_level_divisions WHERE division = '"+ division + "'";
            PreparedStatement pcc = JDBConnection.getConnection().prepareStatement(sqlcc);
            ResultSet r=pcc.executeQuery();
            r.next();
            int division_Id=r.getInt("Division_ID");

            String sqlc = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?)";
            PreparedStatement pc = JDBConnection.getConnection().prepareStatement(sqlc);
            pc.setString(1, customerName);
            pc.setString(2, address);
            pc.setString(3, postalCode);
            pc.setString(4, phone);
            pc.setInt(5, division_Id);

            pc.execute();

        }
        catch(SQLException e){
            e.printStackTrace();
    }
}


    /** This is used to select all the customers.
     * @param customerId gives the customer Id.
     * @param customerName gives the customerName.
     * @param address gives the address.
     * @param postalCode gives the postalCode.
     * @param phone gives the phone number.
     * @param divisionId gives the divisionId.  */
    //update the values
    public static void updateCustomers(int customerId, String customerName, String address, String postalCode, String phone, int divisionId){
    try{
        //update customers
        String sqluc = "UPDATE customers set Customer_Name = ?,  Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps2 = JDBConnection.getConnection().prepareStatement(sqluc);
        ps2.setString(1, customerName);
        ps2.setString(2, address);
        ps2.setString(3, postalCode);
        ps2.setString(4, phone);
        ps2.setInt(5, divisionId);
        ps2.execute();

    } catch (SQLException ex) {
        ex.printStackTrace();

    }

    }

    /** This is used to select the customer and remove from list.
     * @param customerId  will remove the customer. */
    //delete customer information
    public static void deleteCustomer( int customerId){
        try{
            //first_leve_division table is first
            String sqld2 = "DELETE FROM appointments WHERE Customer_ID = ?";
            PreparedStatement pd2 = JDBConnection.getConnection().prepareStatement(sqld2);
            pd2.setInt(1, customerId);
            pd2.execute();

            //Customer table is removed last
            String sqld = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement pd = JDBConnection.getConnection().prepareStatement(sqld);
            pd.setInt(1, customerId);
            pd.execute();
        }

        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
