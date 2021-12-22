package controller;

import DBaccess.DBAppointments;
import DBaccess.DBContacts;
import DBaccess.DBReports;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 *
 * @author Justin Brown
 */

/** This loads the Reports Controller.  */
public class ReportsController implements Initializable {
    @FXML
    private Label customerLbl;
    @FXML
    private TableView<Appointments> reportsTable;
    @FXML
    private TableColumn<Appointments, Integer> appointmentIdCol;
    @FXML
    private TableColumn<Appointments, String> titleCol;
    @FXML
    private TableColumn<Appointments, String> typeCol;
    @FXML
    private TableColumn<Appointments, String> descriptionCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> StartDateTimeCol;
    @FXML
    private TableColumn<Appointments, LocalDateTime> endDateTimeCol;
    @FXML
    private TableColumn<Appointments, Integer> customerIdCol;
    @FXML
    private Label appointmentMonthLbl;
    @FXML
    private Label appointmentPSLbl;
    @FXML
    private Label appointmentDeBLbl;
    @FXML
    private ComboBox<Contacts> contactCB;
    @FXML
    private ComboBox<String> monthCB;
    @FXML
    private TableView<Appointments> monTypeTable;
    @FXML
    private TableColumn<Appointments, String> monTypeCol;
    @FXML
    private TableColumn<Appointments, Integer> monTypeCountCol;

    ObservableList<String> monthList = FXCollections.observableArrayList();

    /** This loads the combo boxes and other reports.  */
    @Override
    public void initialize(URL url, ResourceBundle resources) {

        customerLbl.setText(DBReports.selectNumberCustomers());
        appointmentPSLbl.setText(DBReports.selectAppointmentsPS());
        appointmentDeBLbl.setText(DBReports.selectAppointmentsDeB());
        appointmentMonthLbl.setText(DBReports.selectAppointmentsMonth());

        ObservableList<Contacts> contactList = DBContacts.getAllContacts();
        contactCB.setItems(contactList);

        //monTypeTable.setItems(DBReports.allTheAppointments());
        //monTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        //monTypeCountCol.setCellValueFactory(new PropertyValueFactory<>("typeCount"));


/*
        monthList = FXCollections.observableArrayList();

        monthList.add(new Month(
                new ReadOnlyStringWrapper("January"))),
        monthList.add(new Month(
                new ReadOnlyStringWrapper("February"))),
        contactCB.setItems(monthList);

        colName.setCellValueFactory(cellData -> {
            return cellData.getValue().getName();
        });
        colColor.setCellValueFactory(cellData -> {
            return cellData.getValue().getColor();
        });
        colAge.setCellValueFactory(cellData -> {
            return cellData.getValue().getAge();
        });
        colDate.setCellValueFactory(cellData -> {
            return cellData.getValue().getDate();
        });
        table.setItems(fruitList);


 */

        //fills the combobox with months.
        monthList.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        monthCB.setItems(monthList);
    }


    /** This is used to close the program.  */
    @FXML
    private void onExitBtn(MouseEvent actionEvent) {
            System.exit(0);
    }

    @FXML
    private void onCustomersBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/Customer.fxml")));
        loader.load();

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene= loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /** This is used to change to the appointments view. */
    @FXML
    private void onAppointmentBtn(ActionEvent actionEvent) throws IOException {
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation((getClass().getResource("/view/Appointments.fxml")));
            loader.load();

            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene= loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }


    /** This is what happens when the Contacts Combo box is selected.
     * @return fills out the associated table with the associated contact. */
    //Filters the contacts and displays it on the table.
    @FXML
    private void onContactCB(ActionEvent actionEvent) {
        ObservableList<Appointments> appointments = DBAppointments.getAllMyAppointments();
        FilteredList<Appointments> myAppointments = new FilteredList<>(appointments, i -> i.getContactId() == contactCB.getSelectionModel().getSelectedItem().getContactId());

        reportsTable.setItems(myAppointments);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        StartDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }


    /** This loads the month Combo box.
     * @return this is used to fill the associated table with the data related to the month selected. */
    //pulls data based on the month combo box and fills the Types Table.
    @FXML
    private void onMonthCB(ActionEvent actionEvent) {
        int monthNumber = monthCB.getSelectionModel().getSelectedIndex();
        monthNumber += 1;
        ObservableList<Appointments> Appointments = DBReports.allTheAppointments(monthNumber);

        FilteredList<Appointments> myTypes = new FilteredList<>(Appointments, i-> i.getType() == monthCB.getSelectionModel().getSelectedItem());
/*
        monTypeCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getType();
        });
        monTypeCountCol.setCellValueFactory(cellData -> {
            return cellData.getValue().getTypeCount();
        });

        FilteredList<DBReports> filteredData = new FilteredList<>(monthList);
        filteredData.setPredicate(row -> {

            String rowType = row.getType().getValue();
            int row

            return rowDate.isAfter(now) && rowDate.isBefore(nowPlus7);
        });
        monTypeTable.setItems(filteredData);


 */

        monTypeTable.setItems(myTypes);
        monTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        monTypeCountCol.setCellValueFactory(new PropertyValueFactory<>("typeCount"));
    }
}

