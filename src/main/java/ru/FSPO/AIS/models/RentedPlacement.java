package ru.FSPO.AIS.models;


import java.util.Date;

public class RentedPlacement {

  private long rPlacementId;
  private long totalAmount;
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


  public long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(long totalAmount) {
    this.totalAmount = totalAmount;
  }


  public Date getStartOfRent() {
    return startOfRent;
  }

  public void setStartOfRent(Date startOfRent) {
    this.startOfRent = startOfRent;
  }

  public Date getEndOfRent() {
    return endOfRent;
  }

  public void setEndOfRent(Date endOfRent) {
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

  @Override
  public String toString() {
    return "RentedPlacement{" +
            "rPlacementId=" + rPlacementId +
            ", totalAmount=" + totalAmount +
            ", startOfRent=" + startOfRent +
            ", endOfRent=" + endOfRent +
            ", placementId=" + placementId +
            ", renterId=" + renterId +
            '}';
  }
}
