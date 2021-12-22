package model;

import java.sql.Timestamp;

/**
 *
 * @author Justin Brown
 */

/** This is used to load the Countries */
public class Countries {
    private int countryId;
    private String country;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId;

    public Countries(int countryId, String country, Timestamp createDate,
                     String createdBy, Timestamp lastUpdate, String lastUpdatedBy){
        this.countryId = countryId;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }
    public Countries(int countryId, String country){
        this.countryId = countryId;
        this.country = country;
    }

    public Countries(String country) {
        this.country = country;
    }



    /**
     * @return the Country_ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * @param countryId the country_ID to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * @return the Country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the Create_Date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the create_Date to set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the Created_By
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the created_By to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the Last_Update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the created_By to set
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the Last_Updated_By
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the last_Updated_By to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString(){
        return country;
    }
}
