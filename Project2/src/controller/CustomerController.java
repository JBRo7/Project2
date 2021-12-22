package controller;

import DBaccess.DBAppointments;
import DBaccess.DBCountries;
import DBaccess.DBCustomers;
import Utils.ListProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 *
 * @author Justin Brown
 */


/** This loads Customer Controller.  */
public class CustomerController implements Initializable {
    @FXML
    private TextField customerNameTxt;
    @FXML
    private TextField addressTxt;
    @FXML
    private TextField zipTxt;
    @FXML
    private ComboBox<Countries> countryCB;
    @FXML
    private ComboBox<FirstLevelDivisions> firstLvlDivisionCB;
    @FXML
    private TextField phoneTxt;
    @FXML
    private TableView<Customers> customerTable;
    @FXML
    private TableColumn<Customers, Integer> customerIDCol;
    @FXML
    private TableColumn<Customers, String> customerNameCol;
    @FXML
    private TableColumn<Customers, String> addressCol;
    @FXML
    private TableColumn<Customers, String> zipCol;
    @FXML
    private TableColumn<Customers, String> phoneCol;
    @FXML
    private TableColumn<FirstLevelDivisions, String> firstLvlDivisionCol;
    @FXML
    private TextField customerIdTxt;
    private Customers customerUpdate = null;


    /** This is used to load customer data into the table and display combo boxes. */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        //get all the mysql data into the table
        customerTable.setItems(ListProvider.getAllCustomers());
        //determine the column names
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        firstLvlDivisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));

        //loads countries combobox
        countryCB.setItems(ListProvider.getAllCountries());
        countryCB.getSelectionModel().selectFirst();
        int countryId = countryCB.getSelectionModel().getSelectedItem().getCountryId();

        //loads division combobox
        firstLvlDivisionCB.setItems(generateDivisions(countryId));
        firstLvlDivisionCB.getSelectionModel().selectFirst();
    }


    /** This gives the observable list that is used for the divisions.
     * @param countryId gets the country Id.
     * @return Returns the list of Divisions. */
    //generateDivisions is an observable list used to compare countryId with the divisionId and returns divisions of the country source
    public ObservableList<FirstLevelDivisions> generateDivisions(int countryId){
        ObservableList<FirstLevelDivisions> filteredDivisions= FXCollections.observableArrayList();
        for(FirstLevelDivisions fld:ListProvider.getAllDivisions()){

            if(countryId == fld.getCountryId() ){
                filteredDivisions.add(fld);
            }

        }
        return filteredDivisions;
    }


    /** This clears all the fields in the user selection spaces below. */
    //clearclear fields
    @FXML
    private void onClearFields(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear the entered fields and selections. Press OK to proceed");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
        customerNameTxt.clear();
        addressTxt.clear();
        zipTxt.clear();
        phoneTxt.clear();
        countryCB.getSelectionModel().selectFirst();
        firstLvlDivisionCB.getSelectionModel().selectFirst();

        System.out.println("Fields and selections or reset");
        }
    }


    /** This is used to fill all data into the lower text and combo boxes from the table selection.  */
    //sends all data below to the text fields
    @FXML
    private void onTableClick(MouseEvent mouseEvent) {
        customerUpdate = customerTable.getSelectionModel().getSelectedItem();
        if (customerUpdate != null) {
            customerNameTxt.setText(customerUpdate.getCustomerName());
            addressTxt.setText(customerUpdate.getAddress());
            zipTxt.setText(customerUpdate.getPostalCode());
            phoneTxt.setText(customerUpdate.getPhone());

            //fills out the Comboboxes based on the CountryID
            countryCB.setItems(ListProvider.getAllCountries());
            Countries country = DBCountries.getCountryById(customerUpdate.getCountryId());
            //Countries country = DBCountries.selectCountry(customerUpdate.getCustomerId());

            countryCB.getSelectionModel().select(country);

            if(country != null) {

                //observable list for divisions
                ObservableList<FirstLevelDivisions> divisionList= FXCollections.observableArrayList();
                FirstLevelDivisions div = new FirstLevelDivisions(customerUpdate.getDivision());
                divisionList.add(div);
                firstLvlDivisionCB.getSelectionModel().select(div);

            }
        }
    }


    /** This is used to delete the selected row.
     * @param actionEvent is used to delete a row. */
    @FXML
    public void onDeleteBtn(ActionEvent actionEvent) {

        //Delete selected row
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "The selected customer will be deleted. Press OK to proceed");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            DBCustomers.deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
            DBCustomers.selectCustomer();
            customerTable.setItems(ListProvider.getAllCustomers());
            DBAppointments.selectAppointment();
    }
}

    /** This is used to save a new customer.
     * @param actionEvent  is used to save a customer. */
    //Save button for saving new customer and updating an existing customer
    @FXML
    public void onSaveBtn(ActionEvent actionEvent) {
        try {
            String customerName = customerNameTxt.getText();
            String address = addressTxt.getText();
            String postalCode = zipTxt.getText();
            Countries countries = countryCB.getValue();
            FirstLevelDivisions firstLevelDivisions = firstLvlDivisionCB.getValue();
            String phone = phoneTxt.getText();

            //ensures that all values are entered in the text fields and combo boxes
            if (customerNameTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("A customer name must be entered");
                alert.showAndWait();
                return;
            }
            else if (addressTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("The address must be entered");
                alert.showAndWait();
                return;
            }
            else if (zipTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please select a postal code");
                alert.showAndWait();
                return;
            }

            else if (countries.equals(null)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please select a valid country");
                alert.showAndWait();
                return;
            }
            else if (firstLvlDivisionCB.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please select a valid state or division");
                alert.showAndWait();
                return;
            }

            else if (phoneTxt.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Please select a phone number");
                alert.showAndWait();
                return;
            }

            if (customerUpdate == null) {
                //create a new customer
                DBCustomers.createCustomer(customerName, address, postalCode, phone, firstLevelDivisions.getDivision());
            }

            //update existing customer
            else {
                DBCustomers.updateCustomers(customerUpdate.getCustomerId(), customerName, address, postalCode, phone, customerUpdate.getDivisionId());
            }

            //Update the customer table with the new or updateded customer
            DBCustomers.selectCustomer();
            customerTable.refresh();
            customerTable.setItems(ListProvider.getAllCustomers());

    } catch (NumberFormatException e){
               e.printStackTrace();
        }
    }



    /** This is used to load the appointments page. */
// loads the appointments page
    @FXML
    private void onAppointmentsBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/Appointments.fxml")));
        loader.load();

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene= loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This loads the division combo box based on the countryId. */
    //when the country Combobox is pressed, it triggers the state box to be populated with the appropriate states.
    @FXML
    private void onCountryCB(ActionEvent actionEvent) {
        int countryId = countryCB.getSelectionModel().getSelectedItem().getCountryId();
        firstLvlDivisionCB.setItems(generateDivisions(countryId));
    }

    /** This is used to exit the application. */
    //Exit the application
    @FXML
    private void onExitBtn(MouseEvent mouseEvent) {
            System.exit(0);
    }



    /** This is used to load the reports page. */
    //Button to go to reports page
    @FXML
    private void onReportsBtn(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation((getClass().getResource("/view/Reports.fxml")));
        loader.load();

        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene= loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
