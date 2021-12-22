package DBaccess;

import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBConnection;
import model.Appointments;
import model.Contacts;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 *
 * @author Justin Brown
 */
/** This is used to load the Reports Database. */
public class DBReports {

    /** This is selects all the customers.
     * @return Returns the number of Cusotmers to be displayed. */
    public static String selectNumberCustomers() {

        try {
            String customerNumbers = "SELECT COUNT(Customer_ID) FROM customers";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(customerNumbers);

            ResultSet r = p.executeQuery();
            if (r.next()) {

                //the quotes is the database column names.
                int customerId = r.getInt(1);

                Customers Cust = new Customers(customerId);
                ListProvider.addCustomerNumbers(Cust);
                return String.valueOf(customerId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /** This is used to select all the appointments related to the Planning Sessions.
     * @return gives the appointment by PS. */
    //Gives the total appointment count in relation to Planning sessions
    public static String selectAppointmentsPS() {

        try {
            String appointmentPS = "SELECT COUNT(Appointment_ID) FROM appointments WHERE Type = \"Planning Session\"";

            PreparedStatement ps = JDBConnection.getConnection().prepareStatement(appointmentPS);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                //the quotes is the database column names.
                int appointmentId = rs.getInt(1);

                Appointments appt = new Appointments(appointmentId);
                ListProvider.addAppointmentsPS(appt);
                return String.valueOf(appointmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /** This is used to select all the appointments in relation to the de-briefing.
     * @return gives the appointment by de-briefing. */
    //Gives the total appointment count in relation to De-Briefing
    public static String selectAppointmentsDeB() {

        try {
            String appointmentDeB = "SELECT COUNT(Appointment_ID) FROM appointments WHERE Type = \"De-Briefing\"";

            PreparedStatement ps = JDBConnection.getConnection().prepareStatement(appointmentDeB);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                //the quotes is the database column names.
                int appointmentId = rs.getInt(1);

                Appointments appt = new Appointments(appointmentId);
                ListProvider.addAppointmentsDeB(appt);
                return String.valueOf(appointmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /** This is for the Appointment selection by month.
     * @return month to be returned. */
    //Gives the total appointment count for the current month
    public static String selectAppointmentsMonth() {

        try {
            String appointmentMonth = "SELECT COUNT(Appointment_ID) FROM appointments WHERE MONTH(Start) = MONTH(CURRENT_DATE())";

            PreparedStatement ps = JDBConnection.getConnection().prepareStatement(appointmentMonth);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                //the quotes is the database column names.
                int appointmentId = rs.getInt(1);

                Appointments appt = new Appointments(appointmentId);
                ListProvider.addAppointmentsMonth(appt);
                return String.valueOf(appointmentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }


    /** This gives the appointments. */
    public static void selectReports() {
        ListProvider.getAllReports().clear();

        try {
        String rep ="SELECT * FROM appointments";

        PreparedStatement p = JDBConnection.getConnection().prepareStatement(rep);

        ResultSet r = p.executeQuery();
        while (r.next()) {

                    //the quotes is the database column names.
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String type = r.getString("Type");
                String description = r.getString("Description");
                LocalDateTime start = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");


                Appointments Rep = new Appointments(appointmentId, title, type, description, start, end, customerId);
                ListProvider.addReports(Rep);

            }
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }


    /** This is used to select all the appointments.
     * @param monthNumber the month.
     @return Returns all appointments by month number.  */
// Contact will set up the list
    public static ObservableList<Appointments> allTheAppointments(int monthNumber) {
        ObservableList<Appointments> monthTypeList = FXCollections.observableArrayList();

        int monthNum = monthNumber;

        try {
            String monthType ="SELECT appointments.Type, COUNT(appointments.Type) as TypeCount FROM appointments  WHERE month(Start) = " + monthNum + " GROUP BY TYPE";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(monthType);

            ResultSet r = p.executeQuery();
            while (r.next()) {

                String type = r.getString("Type");
                int typeCount = r.getInt("TypeCount");

                Appointments Rep = new Appointments(type, typeCount);
                monthTypeList.add(Rep);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return monthTypeList;
    }
}

