package main;

import DBaccess.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Justin Brown
 */

/** This is used to load the main. */
public class Main extends Application {


    /** This loads the first page. This is where the user starts. */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserId.fxml"));
        primaryStage.setTitle("Appointments Application");
        primaryStage.setScene(new Scene(root, 409, 359));
        primaryStage.show();


}
    public static void main(String[] args){

        JDBConnection.startConnection();

        DBCountries.selectCountry();
        DBDivisions.selectDivision();
        DBAppointments.selectAppointment();
        DBCustomers.selectCustomer();
        DBContacts.selectContacts();
        DBUsers.selectUsers();
        DBReports.selectNumberCustomers();
        DBReports.selectReports();
        DBAppointments.selectWeekAppointments();
        DBAppointments.selectMonthAppointments();
        DBReports.selectAppointmentsPS();
        DBReports.selectAppointmentsDeB();
        DBReports.selectAppointmentsMonth();


        launch(args);
        JDBConnection.closeConnection();



    }
}


