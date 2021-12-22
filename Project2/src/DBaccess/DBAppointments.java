package DBaccess;

import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.JDBConnection;
import model.Appointments;
import model.Contacts;
import model.Countries;
import model.Users;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author Justin Brown
 */

/** This selects all the appointments. */
public class DBAppointments {

    /** This selects all the appointments. */
    //selects the appointment from the database.
    public static void selectAppointment() {
        ListProvider.getAllAppointments().clear();

        try {
            String appoint ="SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location,  contacts.Contact_Name, contacts.Contact_ID, appointments.Type,"
                    + " appointments.Start, appointments.End, customers.Customer_ID, users.User_ID FROM appointments, customers, users, contacts"
            + " WHERE contacts.Contact_ID = appointments.Contact_ID AND customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(appoint);

            ResultSet r = p.executeQuery();
            while (r.next()) {

                //the quotes is the database column names.
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String description = r.getString("Description");
                String location = r.getString("Location");
                String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime start = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int userId = r.getInt("User_ID");
                int contactId = r.getInt("Contact_ID");

                Appointments A = new Appointments(appointmentId, title, description, location, contactName, type, start, end, customerId, userId, contactId);
                ListProvider.addAppointments(A);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This selects all the appointments by Id.
     * @param id is used to get the appointmentID.
     * @return returns appoint. */
    public static Appointments getAppointmentById(int id) {
        Appointments appoint = null;
        try{
            String sql = "SELECT * From appointments WHERE Appointment_ID = ?";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(sql);
            p.setInt(1,id);

            ResultSet r = p.executeQuery();
            while(r.next()){
                int appointmentId = r.getInt("Appointment_ID");
                String type = r.getString("Type");
                appoint = new Appointments(appointmentId, type);

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appoint;

    }

    /** This selects all the appointments.
     * @param appointmentId by the appointment Id. */
    //delete the appointment
    public static void deleteAppointments( int appointmentId){
        try{
            String sqld2 = "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement pd2 = JDBConnection.getConnection().prepareStatement(sqld2);
            pd2.setInt(1, appointmentId);
            pd2.execute();
        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }

    /** This selects all the appointments.
     * @param title by title.
     * @param description by description.
     * @param location by location.
     * @param type by type.
     * @param start by start.
     * @param end by end.
     * @param customerId by customer Id.
     * @param userId by userId.
     * @param contactId by the contact Id. */
    public static void createAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId){
        try{
            //Insert appointments table
            String sqlc = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?, ?, NOW(), '', NOW(), '', ?, ?, ?)";

            PreparedStatement pc = JDBConnection.getConnection().prepareStatement(sqlc);
            pc.setString(1, title);
            pc.setString(2, description);
            pc.setString(3, location);
            pc.setString(4, type);
            pc.setTimestamp(5, Timestamp.valueOf(start));
            pc.setTimestamp(6, Timestamp.valueOf(end));
            pc.setInt(7, customerId);
            pc.setInt(8, userId);
            pc.setInt(9, contactId);
            pc.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    /** This gives an updated appointment.
     * @param customerId by customerId.
     * @param title by the title.
     * @param location by the location.
     * @param description by the description.
     * @param type by the type.
     * @param start by the start.
     * @param end by the end.
     * @param contactId by the contactId.
     * @param userId by the userId.
     * @param appointmentId by the appointmentId. */
        //Updates an existing appointment.
    public static void updateAppointment (int appointmentId, String title, String description, String location,  String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId){
        try{
            String sqlcu = "UPDATE appointments set Title = ?,  Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement pbcu = JDBConnection.getConnection().prepareStatement(sqlcu);
            pbcu.setString(1, title);
            pbcu.setString(2, description);
            pbcu.setString(3, location);
            pbcu.setString(4, type);
            pbcu.setTimestamp(5, Timestamp.valueOf(start));
            pbcu.setTimestamp(6, Timestamp.valueOf(end));
            pbcu.setInt(7, customerId);
            pbcu.setInt(8, userId);
            pbcu.setInt(9, contactId);
            pbcu.setInt(10, appointmentId);

            pbcu.execute();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    /** This selects all the appointments in a given week. */
    //view appointments in the current week
    public static void selectWeekAppointments() {

        try {String appoint2Week = "SELECT * FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID and week(Start) = week(curdate())";
                //"SELECT * FROM appointments WHERE week(Start) = week(curdate())";
                //needed to change to current week.
                // "SELECT * FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID and Start <= curdate() and Start >= date_sub(curdate(), INTERVAL 15 DAY)";
                //"SELECT * FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID AND Start >= (now() - interval 14 day)"
                //"SELECT * FROM client_schedule.appointments WHERE Start <= curdate() and Start >= date_sub(curdate(), INTERVAL 15 DAY)";

            PreparedStatement p = JDBConnection.getConnection().prepareStatement(appoint2Week);

            ResultSet r = p.executeQuery();
            while (r.next()) {
                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String description = r.getString("Description");
                String location = r.getString("Location");
                String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime start = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int userId = r.getInt("User_ID");
                int contactId = r.getInt("Contact_ID");


                Appointments Ap2W = new Appointments(appointmentId, title, description, location, contactName, type, start, end, customerId, userId, contactId);
                ListProvider.addAppointments2Weeks(Ap2W);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /** This selects all the appointments in a given month. */
    //view appointments in the current month
    public static void selectMonthAppointments() {
        try {
            String appointMonth = "SELECT * FROM appointments, contacts WHERE appointments.Contact_ID = contacts.Contact_ID AND MONTH(Start) = MONTH(CURRENT_DATE())";
            PreparedStatement prep = JDBConnection.getConnection().prepareStatement(appointMonth);

            ResultSet r = prep.executeQuery();
            while (r.next()) {

                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String description = r.getString("Description");
                String location = r.getString("Location");
                String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime start = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int userId = r.getInt("User_ID");
                int contactId = r.getInt("Contact_ID");


                Appointments ApM = new Appointments(appointmentId, title, description, location, contactName, type, start, end, customerId, userId, contactId);
                ListProvider.addMonthAppointments(ApM);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** This selects all the appointments.
     * @return returns an observable list. */

    public static ObservableList<Appointments> getAllMyAppointments() {
            ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();
        try {
            String appt ="SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location,  contacts.Contact_Name, contacts.Contact_ID, appointments.Type,"
                    + " appointments.Start, appointments.End, customers.Customer_ID, users.User_ID FROM appointments, customers, users, contacts"
                    + " WHERE contacts.Contact_ID = appointments.Contact_ID AND customers.Customer_ID = appointments.Customer_ID AND users.User_ID = appointments.User_ID";
            PreparedStatement p = JDBConnection.getConnection().prepareStatement(appt);

            ResultSet r = p.executeQuery();
            while (r.next()) {

                int appointmentId = r.getInt("Appointment_ID");
                String title = r.getString("Title");
                String description = r.getString("Description");
                String location = r.getString("Location");
                String contactName = r.getString("Contact_Name");
                String type = r.getString("Type");
                LocalDateTime start = r.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = r.getTimestamp("End").toLocalDateTime();
                int customerId = r.getInt("Customer_ID");
                int userId = r.getInt("User_ID");
                int contactId = r.getInt("Contact_ID");

                Appointments Appt = new Appointments(appointmentId, title, description, location,contactName, type, start, end, customerId, userId, contactId);
                appointmentList.add(Appt);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }
}
