package controller;

import DBaccess.DBAppointments;
import DBaccess.DBContacts;
import DBaccess.DBCustomers;
import DBaccess.DBUsers;
import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Justin Brown
 */
/** This loads the appointments controller */
public class AppointmentsController implements Initializable {

        @FXML
        private TableView<Appointments> appointmentsTable;

        @FXML
        private TableColumn<Appointments, Integer> appointmentIdCol;

        @FXML
        private TableColumn<Appointments, Integer> customerIdCol;

        @FXML
        private TableColumn<Appointments, String> titleCol;

        @FXML
        private TableColumn<Appointments, String> descriptionCol;

        @FXML
        private TableColumn<Appointments, String> locationCol;

        @FXML
        private TableColumn<Appointments, String> contactCol;
        @FXML
        private TableColumn<Appointments, String> typeCol;
        @FXML
        private TableColumn<Appointments, LocalDateTime> startCol;
        @FXML
        private TableColumn<Appointments, LocalDateTime> endCol;
        @FXML
        private TableColumn<Appointments, Integer> userIdCol;

        @FXML
        private TextField titleTxt;

        @FXML
        private TextField descriptionTxt;

        @FXML
        private TextField locationTxt;

        @FXML
        private ComboBox<Contacts> contactCB;
        @FXML
        private DatePicker datePicker;
        @FXML
        private ComboBox<LocalTime> startTimeCB;
        @FXML
        private ComboBox<Customers> customerIdCB;
        @FXML
        private ComboBox<Users> userIdCB;
        @FXML
        private ComboBox typeCB;

        ObservableList<Appointments> typeList = FXCollections.observableArrayList();
        //"De-Briefing", "Planning Session, "

        ObservableList<Users> myUsers = FXCollections.observableArrayList();

        private Appointments appointmentsUpdate = null;
        @FXML
        private ComboBox<LocalTime> endTimeCB;

/*
        // observable array list for the Types. This holds the getters and setters.
        private ObservableList<String> getTypeList() {
                return typeList;
        }

        private void setTypeList(ObservableList<String> typeList) {
                this.typeList = typeList;
        }



 */


        /** This loads the appointment data into the table. */
        @Override
        public void initialize(URL url, ResourceBundle resources) {
                //ObservableList<Users> myUsers = approachingAppointment;
//12            upComingAppoint = myUsers;



                //get all the mysql data into the table
                appointmentsTable.setItems(ListProvider.getAllAppointments());

                //determine the column names
                appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
                typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
                endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
                customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

                //fills out the contact combo box
                //contactCB.setItems(ListProvider.getAllContacts());
                contactCB.setItems(DBContacts.getAllContacts());
                contactCB.getSelectionModel().selectFirst();
//contactCB.setPromptText("Select Contact");
                int contactId = contactCB.getSelectionModel().getSelectedItem().getContactId();

                // fills customer combo box.
                customerIdCB.setItems(ListProvider.getAllCustomers());
                customerIdCB.getSelectionModel().selectFirst();
                int customerId = customerIdCB.getSelectionModel().getSelectedItem().getCustomerId();

                // fills userId combo box
                userIdCB.setItems(DBUsers.getAllUsers());
                userIdCB.getSelectionModel().selectFirst();

                //fills the type combo box with the list of types.
                typeList.add(new Appointments("Planning Session"));
                typeList.add(new Appointments("De-Briefing"));
                typeList.add(new Appointments("Consultation"));

                typeCB.setItems(typeList);
                typeCB.getSelectionModel().selectFirst();

                datePicker.getTypeSelector();
                LocalDate localDate = datePicker.getValue();
                datePicker.setValue(LocalDate.now());

                //set hour and min of the combo box startTime
                LocalTime start1 = LocalTime.of(8, 0);
                LocalTime end1 = LocalTime.of(21, 45);
                while(start1.isBefore(end1.plusSeconds(1))) {
                        startTimeCB.getItems().add(start1);
                        start1 = start1.plusMinutes(15);
                }

                startTimeCB.getSelectionModel().select(LocalTime.of(9, 0));


                //set hour and min of the combo box endTime
                LocalTime start2 = LocalTime.of(8, 15);
                LocalTime end2 = LocalTime.of(22, 0);
                while(start2.isBefore(end2.plusSeconds(1))) {
                        endTimeCB.getItems().add(start2);
                        start2 = start2.plusMinutes(15);
                }
                endTimeCB.getSelectionModel().select(LocalTime.of(9, 45));


/** lambda used for filtering the combo boxes. */
//on table click the data will be pulled down to the below combo boxes and text fields.
                appointmentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                        if (newSelection != null) {
                                for (int i = 0; i <userIdCB.getItems().size(); i++){
                                        Users users = userIdCB.getItems().get(i);
                                        if (users.getUserId() == newSelection.getUserId()) {
                                                userIdCB.getSelectionModel().select(i);
                                                break;
                                        }
                                }

                                for (int i = 0; i < contactCB.getItems().size(); i++){
                                        Contacts contacts = contactCB.getItems().get(i);
                                        if (contacts.getContactId() == newSelection.getContactId()){
                                                contactCB.getSelectionModel().select(i);
                                                break;
                                        }
                                }
                                for (int i = 0; i < customerIdCB.getItems().size(); i++){
                                        Customers customers = customerIdCB.getItems().get(i);
                                        if (customers.getCustomerId() == newSelection.getCustomerId()){
                                                customerIdCB.getSelectionModel().select(i);
                                                break;
                                        }
                                }
//12
                                for (int i = 0; i < typeCB.getItems().size(); i++){
                                        Appointments appointments = (Appointments) typeCB.getItems().get(i);
                                        if (appointments.getType() == newSelection.getType()){
                                        //Appointments appointments = typeCB.getValue(Appointments.typesList);
                                        //if (appointments.getType() == newSelection.getType()){
                                                typeCB.getSelectionModel().select(i);
                                                break;
                                        }
                                }




                        }

                });
                }



        /** This is used to delete an appointment. */
        @FXML
        private void onDeleteBtn(ActionEvent actionEvent) {
                //Delete selected row
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected appointment will be deleted. Press OK to proceed?");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                        DBAppointments.deleteAppointments(appointmentsTable.getSelectionModel().getSelectedItem().getAppointmentId());
                        DBAppointments.selectAppointment();
                        appointmentsTable.setItems(ListProvider.getAllAppointments());
                        DBAppointments.selectAppointment();
                }
        }


        /** This is used to clear all the fields below. */
        @FXML
        private void onClearFields(ActionEvent actionEvent) {

                Alert alert = new Alert(Alert.AlertType.WARNING, "This will clear the entered fields and selections. Do you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                        //clear fields
                        titleTxt.clear();
                        descriptionTxt.clear();
                        locationTxt.clear();
                        //contactCB.getSelectionModel().clearSelection();
                        contactCB.getSelectionModel().selectFirst();
                        typeCB.getSelectionModel().selectFirst();
                        customerIdCB.getSelectionModel().selectFirst();
                        userIdCB.getSelectionModel().selectFirst();
                        //datePicker.getTypeSelector();
                        datePicker.setValue(LocalDate.now());
                        //startTimeCB.getSelectionModel().selectFirst();
                        startTimeCB.setValue(LocalTime.of(9, 0));
                        endTimeCB.setValue(LocalTime.of(9, 45));

                        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Text fields were cleared, dropdowns were reset, and the current date was selected.");
                        Optional<ButtonType> result2 = alert2.showAndWait();
                }
        }

        /** This is used to load all the data into the combo boxes and text boxes.
         * @return loads the selected row. */
        @FXML
        private void onTableClick(MouseEvent mouseEvent) {
                appointmentsUpdate = appointmentsTable.getSelectionModel().getSelectedItem();

                if (appointmentsUpdate != null) {
                        titleTxt.setText(appointmentsUpdate.getTitle());
                        descriptionTxt.setText(appointmentsUpdate.getDescription());
                        locationTxt.setText(appointmentsUpdate.getLocation());

                        // fills type combobox with the tableview data
// 12      typeCB.setValue(appointmentsUpdate.getType());
                        //typeCB.setItems(appointmentsUpdate.getType(typeList));
 //                       typeCB.setValue(appointmentsUpdate.getType());

                        //Fills the datePicker with the date from the tableview above.
                        datePicker.setValue(appointmentsUpdate.getStart().toLocalDate());

                        //set the start combo box to the selected table value.
                        startTimeCB.setValue(appointmentsUpdate.getStart().toLocalTime());
                        startTimeCB.setVisibleRowCount(6);

                        //set the end combo box to the selected table value.
                        endTimeCB.setValue(appointmentsUpdate.getEnd().toLocalTime());
                        endTimeCB.setVisibleRowCount(6);
                }
        }

        /** This is used to save a new appointment.  */
        //Saves the appointment
        @FXML
        private void onSaveBtn(ActionEvent actionEvent) {

                try {
                        String title = titleTxt.getText();
                        String description = descriptionTxt.getText();
                        String location = locationTxt.getText();
                        Contacts contact = contactCB.getValue();
                        //String type = typeCB.getSelectionModel().toString();
                        String type = typeCB.getValue().toString();
                        LocalDate startDate = datePicker.getValue();
                        LocalDate endDate = datePicker.getValue();
                        LocalTime startTime = startTimeCB.getValue();
                        LocalTime endTime = endTimeCB.getValue();
                        Customers customers = customerIdCB.getValue();
                        Users users = userIdCB.getValue();

                        //Links the date and times back together for the database
                        LocalDateTime start = LocalDateTime.of(startDate, startTime);
                        LocalDateTime end = LocalDateTime.of(endDate, endTime);


                        //ensures that all values are entered in the text fields and combo boxes
                        if (titleTxt.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please enter a title for the appointment");
                                alert.showAndWait();
                                return;
                        }
                        if (descriptionTxt.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please enter a description for the appointment");
                                alert.showAndWait();
                                return;
                        }
                        if (locationTxt.getText().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please enter a location for the appointment");
                                alert.showAndWait();
                                return;
                        }
                        if (contactCB.getSelectionModel().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please select a contact");
                                alert.showAndWait();
                                return;
                        }
                        if (typeCB.getSelectionModel().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please select a type");
                                alert.showAndWait();
                                return;
                        }

                        if (datePicker == null) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please select the date for the appointment");
                                alert.showAndWait();
                                return;
                        }

                        // Ensures the current date or a future date is selected.
                        if (datePicker.getValue().isBefore(LocalDate.now())) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Appointments can only be set for a future date");
                                alert.showAndWait();
                                return;
                        }

                        if (startTimeCB.getSelectionModel().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please select a starting time for the appointment");
                                alert.showAndWait();
                                return;
                        }
                        if (endTimeCB.getSelectionModel().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please select a ending time for the appointment");
                                alert.showAndWait();
                                return;
                        }
                        // the start date must be before the end date.
                        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("The start time must be before the end time");
                                alert.showAndWait();
                                return;
                        }
                        if (customerIdCB.getSelectionModel().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please select a Customer ID");
                                alert.showAndWait();
                                return;
                        }
                        if (userIdCB.getSelectionModel().isEmpty()) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Warning");
                                alert.setContentText("Please elect a User ID");
                                alert.showAndWait();
                                return;
                        }


                        //create a new appointment
  //12 how to relate to customer Id and appointmentId
  /*
                                if(startTime.isBefore(endTime) || startTime.equals(endTime)) {
                                      //  For(customerId and appointmentId)
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error Dialog");
                                        alert.setContentText("The customer cannot schedule two appointments at the same time.");
                                        alert.showAndWait();
                                        return;
                                }



   /*                     else (startTime.isBefore()){
                                        for(Customers.)

                                }


   */
                        if (appointmentsUpdate == null) {
                                DBAppointments.createAppointment(title, description, location, type, start, end, customers.getCustomerId(), users.getUserId(), contact.getContactId());

                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "A new appointment has been added.");
                                Optional<ButtonType> result = alert.showAndWait();
                        }


                        //update existing appointment
                        else {
                                DBAppointments.updateAppointment(appointmentsUpdate.getAppointmentId(), title, description, location, appointmentsUpdate.getType(), start, end, appointmentsUpdate.getCustomerId(), appointmentsUpdate.getUserId(), appointmentsUpdate.getContactId());
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The appointment was successfully updated.");
                                Optional<ButtonType> result = alert.showAndWait();
                        }

                        //Update the customer table with the new entry
                        DBAppointments.selectAppointment();
                        appointmentsTable.refresh();
                        appointmentsTable.setItems(ListProvider.getAllAppointments());

                } catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Please enter a valid value for each text field or drop down");
                alert.showAndWait();
        }
}



        /** This is used to go to the customer page.  */
    @FXML
    private void onBackToCustomersBtn(ActionEvent actionEvent) throws IOException {
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation((getClass().getResource("/view/Customer.fxml")));
            loader.load();

            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene= loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
    }


        /** This loads the radio buttons with all the appointments. */
        // sets up the table with all available appointment data
        @FXML
        private void onAllRadioBtn(ActionEvent actionEvent) {
                appointmentsTable.setItems(ListProvider.getAllAppointments());
                appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
                typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
                endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
                customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
                appointmentsTable.refresh();
        }

        /** This loads the table with the current week data. */
        //Sets up the table with the current week appointments data.
        @FXML
        private void onWeekRadioBtn(ActionEvent actionEvent) {
                appointmentsTable.setItems(ListProvider.getAllAppointments2Weeks());
                appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
                typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
                endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
                customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
                appointmentsTable.refresh();
        }

        /** This loads the month data into the table.  */
        //sets up the table with the current month data
        @FXML
        private void onMonthRadioBtn(ActionEvent actionEvent) {
                appointmentsTable.setItems(ListProvider.getAllMonthAppointments());
                appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
                descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
                locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
                contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
                typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
                endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
                customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
                appointmentsTable.refresh();
        }


        /** This exits the program. */
        @FXML
        private void onExitButton(MouseEvent mouseEvent) {
                        System.exit(0);
        }


        /** This loads the Reports page. */
        @FXML
        private void onViewReports(ActionEvent actionEvent) throws IOException{
                FXMLLoader loader= new FXMLLoader();
                loader.setLocation((getClass().getResource("/view/Reports.fxml")));
                loader.load();

                Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                Parent scene= loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
        }
}
