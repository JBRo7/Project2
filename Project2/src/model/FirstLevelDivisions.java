package model;

import java.time.LocalDateTime;


/**
 *
 * @author Justin Brown
 */
public class FirstLevelDivisions {
        private int divisionId;
        private String division;
        private LocalDateTime createDate;
        private String createdBy;
        private LocalDateTime lastUpdate;
        private String lastUpdatedBy;
        private int countryId;

        public FirstLevelDivisions(int divisionId, String division, LocalDateTime createDate,
                                   String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryId){
            this.divisionId = divisionId;
            this.division = division;
            this.createDate = createDate;
            this.createdBy = createdBy;
            this.lastUpdate = lastUpdate;
            this.lastUpdatedBy = lastUpdatedBy;
            this.countryId = countryId;
        }
    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }
    public FirstLevelDivisions(int divisionId, String division){
        this.divisionId = divisionId;
        this.division = division;
    }

    public FirstLevelDivisions(String division){
        this.division = division;
    }

    /**
     * @return the divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }
    /**
     * @param divisionId the divisionId to set
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /**
     * @return the division
     */
    public String getDivision() {
        return division;
    }
    /**
     * @param division the division to set
     */
    public void setDivision(String division) {
        this.division = division;
    }
    /**
     * @return the createDate
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }
    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }
    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    /**
     * @return the lastUpdate
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    /**
     * @return the lastUpdatedBy
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    /**
     * @param lastUpdatedBy the lastUpdatedBy to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    /**
     * @return the countryId
     */
    public int getCountryId() {
        return countryId;
    }
    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString(){
        return division;
    }
}
