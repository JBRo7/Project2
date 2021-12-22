package model;

/**
 *
 * @author Justin Brown
 */

/** This is used to load the Contacts. */
public class Contacts {
    private int contactId;
    private String contactName;

    public Contacts(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }
    /**
     * @return the Contact_ID
     */
    public int getContactId() {
        return contactId;
    }
    /**
     * @param contactId the contact_ID to set
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /**
     * @return the Contact_Name
     */
    public String getContactName() {
        return contactName;
    }
    /**
     * @param contactName the contact_Name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public String toString(){
        return contactName;
    }
}
