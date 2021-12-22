package Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

/**
 *
 * @author Justin Brown
 */

public abstract class ListProvider {

//Countries observable lists, add and get
private static ObservableList<Countries> allCountries = FXCollections.observableArrayList();

public static void addCountry(Countries country){
        allCountries.add(country);
    }

public static ObservableList<Countries>getAllCountries(){
        return allCountries;
    }


//Customers observable lists
private static ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

public static void addCustomers(Customers customers){
        allCustomers.add(customers);
    }
public static ObservableList<Customers>getAllCustomers(){
        return allCustomers;
    }


//CustomerNumbers observable lists
private static ObservableList<Customers> allCustomerNumbers = FXCollections.observableArrayList();

public static void addCustomerNumbers(Customers customers){
        allCustomerNumbers.add(customers);
    }
public static ObservableList<Customers>getAllCustomerNumbers(){
        return allCustomerNumbers;
    }



//UserID observable lists
private static ObservableList<Users> allUsers = FXCollections.observableArrayList();
public static void addUsers(Users users){
        allUsers.add(users);
    }

public static ObservableList<Users>getAllUsers(){
        return allUsers;
    }



//FirstLevelDivisions observable lists
private static ObservableList<FirstLevelDivisions> allDivisions = FXCollections.observableArrayList();
public static void addDivisions(FirstLevelDivisions division){
        allDivisions.add(division);
    }

public static ObservableList<FirstLevelDivisions>getAllDivisions(){
        return allDivisions;
    }


//Contacts observable lists
private static ObservableList<Contacts> allContacts = FXCollections.observableArrayList();

public static void addContacts(Contacts contacts){
        allContacts.add(contacts);
    }

public static ObservableList<Contacts>getAllContacts(){
        return allContacts;
    }


//Reports observable lists
private static ObservableList<Appointments> allReports = FXCollections.observableArrayList();

public static void addReports(Appointments appointments){
        allReports.add(appointments);
    }

public static ObservableList<Appointments>getAllReports(){
        return allReports;
    }



    //====== Appointments
//=================================================================================================

    //Appointments observable lists
    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();

    public static void addAppointments(Appointments appointments){
        allAppointments.add(appointments);
    }

    public static ObservableList<Appointments>getAllAppointments(){
        return allAppointments;
    }


    //Appointments2Weeks observable lists
    private static ObservableList<Appointments> allAppointments2Weeks = FXCollections.observableArrayList();

    public static void addAppointments2Weeks(Appointments appointments){
        allAppointments2Weeks.add(appointments);
    }

    public static ObservableList<Appointments>getAllAppointments2Weeks(){
        return allAppointments2Weeks;
    }


    //AppointmentsMonth observable lists
    private static ObservableList<Appointments> allMonthAppointments = FXCollections.observableArrayList();

    public static void addMonthAppointments(Appointments appointments){
        allMonthAppointments.add(appointments);
    }

    public static ObservableList<Appointments>getAllMonthAppointments(){
        return allMonthAppointments;
    }




    //Appointments Planning Session with a count
    private static ObservableList<Appointments> allAppointmentsPS = FXCollections.observableArrayList();

    public static void addAppointmentsPS(Appointments appointments){
        allAppointmentsPS.add(appointments);
    }

    public static ObservableList<Appointments>getAllAppointmentsPS(){
        return allAppointmentsPS;
    }


    //Appointments De-Briefing with a count
    private static ObservableList<Appointments> allAppointmentsDeB = FXCollections.observableArrayList();

    public static void addAppointmentsDeB(Appointments appointments){
        allAppointmentsDeB.add(appointments);
    }

    public static ObservableList<Appointments>getAllAppointmentsDeB(){
        return allAppointmentsDeB;
    }


    //Appointments for the month with a count
    private static ObservableList<Appointments> allAppointmentsMonth = FXCollections.observableArrayList();

    public static void addAppointmentsMonth(Appointments appointments){
        allAppointmentsMonth.add(appointments);
    }

    public static ObservableList<Appointments>getAllAppointmentsMonth(){
        return allAppointmentsMonth;
    }


//===========================================================================================
//=======

}
