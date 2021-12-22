package controller;

import DBaccess.DBAppointments;
import DBaccess.DBUsers;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.JDBConnection;
import model.Appointments;
import model.Users;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.ResultSet;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Justin Brown
 */

/** This is used for the UserId Controller. */
public class UserIDController implements Initializable {
    @FXML
    private Label loginErrorMsg;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Label zoneIdLabel;

    int userId;
    String appointMin;



    /** This loads the timezone.  */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Displays the user geographical location to the zoneIdLabel
        ZoneId zone = ZoneId.systemDefault();
        zoneIdLabel.setText(zone.getId());
    }

    /** This loads the login button.
     * @return checks user login credentials. */
    @FXML
    private void onLoginBtn(ActionEvent actionEvent) throws SQLException, IOException {

        Connection connection = JDBConnection.getConnection(); // Reference Connection object
        System.out.println("Connection successful!");
        if (username.getText().isBlank() == false && password.getText().isBlank() == false){
            String login = "SELECT * FROM client_schedule.users WHERE User_Name = '" + username.getText() + "' AND Password = '" + password.getText() + "'";

            try {
                PreparedStatement statement = connection.prepareStatement(login);
                ResultSet queryResult = statement.executeQuery(login);
                while (queryResult.next()) {
                    if (queryResult.getInt(1) == 1) {
                        loginAttempts(username.getText());

                        //loginErrorMsg.setText("Successful");
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation((getClass().getResource("/view/Appointments.fxml")));
                        loader.load();

                        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();

                        //checks for any upcoming appointments
                        Alert();

                    } else {
                        loginErrorMsg.setText("Invalid login");
                        loginAttempts(username.getText());
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                e.getCause();


            }

        } else{
            loginErrorMsg.setText("Please enter a valid username and password");
            return;
        }

        // language is in French
        try {
            ResourceBundle rb = ResourceBundle.getBundle("main/NAT_fr", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr"))
                System.out.println(rb.getString("hello"));

            //test language . only used when checking for french
            //Locale.setDefault(new Locale("fr"));

        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }
    }


    /** This is used to check for approaching appointments.
     * @return Returns appointment if it is within the next 15 minutes. */
// warns of an appointment is approaching in 15 min or not
    private void Alert() {
        ObservableList<Users> myUsers = DBUsers.getAllUsers();

        for (int i = 0; i < myUsers.size(); i++) {
            if ((myUsers.get(i).getUserName().equals(username))) {
                //) && (myUsers.get(i).getPassword().equals(password)
                userId = myUsers.get(i).getUserId();
            }
        }
        ObservableList<Appointments> appointments = DBAppointments.getAllMyAppointments();
        FilteredList<Appointments> userAppoint = new FilteredList<>(appointments, i -> i.getUserId() == userId);

        for (int a = 0; a < userAppoint.size(); a++) {
            Appointments appointment = userAppoint.get(a);
            LocalTime Start = LocalTime.of(23, 59);
            LocalTime curTime = LocalTime.now();
            long diffTimes = ChronoUnit.MINUTES.between(Start, curTime);
            long interval = diffTimes * -1;

            if (interval > 0 && interval <= 15) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Approaching appointment, Appointment ID: " + appointment.getAppointmentId() + ", " + LocalDate.now() + ", " + LocalTime.now());
                alert.showAndWait();
                System.out.println("An appointment will start in " + interval + " minute(s)!");
            }
            //else if(interval <= 0);
            //  System.out.println("An appointment started " +interval * -1 + " minute(s) ago!");
            else if (interval <= 0 || interval > 15) {
                //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "There are no appointments within the next 15 minutes!");
                //Optional<ButtonType> result = alert.showAndWait();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("There are no approaching appointments");
                alert.showAndWait();

            }
        }
    }

    /** This is used to record the login attempts. */
    private void loginAttempts (String username) {
        try {
            System.out.println("The login was successful");
            BufferedWriter writesToFiles = new BufferedWriter(new FileWriter("login_activity.txt", true));
            writesToFiles.newLine();
            writesToFiles.write("Username: " + username + ", Date and Time: " + LocalDateTime.now());
            writesToFiles.close();
        } catch (IOException e) {
            System.out.println("The login was not successful. " + e.getStackTrace());
        }
    }

    /**
     * @param mouseEvent exit application.
     */
    @FXML
    public void onExitBtn(MouseEvent mouseEvent) {
        System.exit(0);
    }

}


