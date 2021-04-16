package ru.FSPO.AIS.models;


import java.sql.Date;

public class RentedPlacement {

  private long rPlacementId;
  private long rent;
  private Date startOfRent;
  private Date endOfRent;
  private long placementId;
  private long renterId;


  public long getRPlacementId() {
    return rPlacementId;
  }

  public void setRPlacementId(long rPlacementId) {
    this.rPlacementId = rPlacementId;
  }


  public long getRent() {
    return rent;
  }

  public void setRent(long rent) {
    this.rent = rent;
  }


  public java.sql.Date getStartOfRent() {
    return startOfRent;
  }

  public void setStartOfRent(java.sql.Date startOfRent) {
    this.startOfRent = startOfRent;
  }


  public java.sql.Date getEndOfRent() {
    return endOfRent;
  }

  public void setEndOfRent(java.sql.Date endOfRent) {
    this.endOfRent = endOfRent;
  }


  public long getPlacementId() {
    return placementId;
  }

  public void setPlacementId(long placementId) {
    this.placementId = placementId;
  }


  public long getRenterId() {
    return renterId;
  }

  public void setRenterId(long renterId) {
    this.renterId = renterId;
  }

}
