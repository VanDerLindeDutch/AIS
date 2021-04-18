package ru.FSPO.AIS.models;


import java.time.Month;
import java.util.Date;

public class RequestToBcLink {

  private long requestId;
  private long placementId;
  private Date expirationDate;
  private long renterId;
  private Date startOfRent;
  private Date endOfRent;
  private boolean isCheked;
  private long totalAmount;

  public long getRequestId() {
    return requestId;
  }

  public void setRequestId(long requestId) {
    this.requestId = requestId;
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


  public Date getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
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


  public boolean isCheked() {
    return isCheked;
  }

  public void setCheked(boolean cheked) {
    isCheked = cheked;
  }

  public long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(long totalAmount) {
    this.totalAmount = totalAmount;
  }


  @Override
  public String toString() {
    return "RequestToBcLink{" +
            "requestId=" + requestId +
            ", placementId=" + placementId +
            ", expirationDate=" + expirationDate +
            ", renterId=" + renterId +
            ", startOfRent=" + startOfRent +
            ", endOfRent=" + endOfRent +
            ", isCheked=" + isCheked +
            ", totalAmount=" + totalAmount +
            '}';
  }
}