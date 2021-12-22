package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;

/**
 *
 * @author Justin Brown
 */


/** This is used to load the Appointments. */
public class Appointments {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String contactName;
    private String type;
    private String month;
    private int typeCount;
    private LocalDateTime start;
    private LocalDateTime end;
    private String customerName;
    private int customerId, userId, contactId;

    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public Appointments(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        //String contactName.
        this.title = title;
        this.description = description;
        this.location = location;
        //this.contactName = contactName;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }



    public Appointments(int appointmentId, String type) {
        this.appointmentId = appointmentId;
        this.type = type;
    }

    public Appointments(int appointmentId, String title, String type, String description, LocalDateTime start, LocalDateTime end, int customerId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.type = type;
        this.description = description;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
    }

    public Appointments(int appointmentId) {
        this.appointmentId = appointmentId;
    }


    public Appointments(String type) {
        this.type = type;
    }
    
    public static ObservableList<Appointments> monthList = FXCollections.observableArrayList();
    public static ObservableList<Appointments> typesList = FXCollections.observableArrayList();

    public Appointments(int appointmentId, String title, String description, String location, String contactName, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactName = contactName;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    public int getTypeCount() {
        return typeCount;
    }

    public void setTypeCount(int typeCount) {
        this.typeCount = typeCount;
    }

    public Appointments(String type, int typeCount) {
        this.type = type;
        this.typeCount = typeCount;
    }


    /**
     * @return the Appointment_ID
     */
        public int getAppointmentId() {
            return appointmentId;
        }
    /**
     * @param appointmentId the appointment_ID to set
     */
        public void setAppointmentId(int appointmentId){
            this.appointmentId = appointmentId;
        }
    /**
     * @return the Title
     */
        public String getTitle () {
            return title;
        }
    /**
     * @param title the title to set
     */
        public void setTitle (String title){
            this.title = title;
        }

    /**
     * @return the Description
     */
        public String getDescription () {
            return description;
        }
    /**
     * @param description the description to set
     */
        public void setDescription (String description){
            this.description = description;
        }
    /**
     * @return the Location
     */
        public String getLocation () {
            return location;
        }
    /**
     * @param location the location to set
     */
        public void setLocation (String location){
            this.location = location;
        }
    /**
     * @return the Type
     */
        public String getType () {
            return type;
        }
    /**
     * @param type the type to set
     */
        public void setType (String type){
            this.type = type;
        }
    /**
     * @return the Customer_ID
     */
        public int getCustomerId() {
            return customerId;
        }
    /**
     * @param customerId the customer_ID to set.
     */
        public void setCustomerId(int customerId){
            this.customerId = customerId;
        }
    /**
     * @return the User_ID.
     */
        public int getUserId() {
            return userId;
        }
    /**
     * @param userId the user_ID to set.
     */
        public void setUserId(int userId){
            this.userId = userId;
        }
    /**
     * @return the Contact_ID.
     */
        public int getContactId() {
            return contactId;
        }
    /**
     * @param contactId the contact_ID to set.
     */
        public void setContactId(int contactId){
            this.contactId = contactId;
        }

    /**
     * @return the ContactName.
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * @param contactName the contactName to set.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    /**
     * @return the CustomerName
     */
    public String getCustomerName() {
        return customerName;
    }
    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }


    /**
     * @return the start
     */
    public LocalDateTime getStart() {
        return start;
    }
    /**
     * @param start the start to set
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    /**
     * @return the end
     */
    public LocalDateTime getEnd() {
        return end;
    }
    /**
     * @param end the end to set
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }


    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }
    /**
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    @Override
  public String toString(){
        return type;
    }

}
