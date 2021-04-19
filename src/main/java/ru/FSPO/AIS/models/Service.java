package ru.FSPO.AIS.models;


public class Service {

  private long serviceId;
  private String description;

  public Service() {
  }


  public Service(long serviceId, String description) {
    this.serviceId = serviceId;
    this.description = description;
  }

  public long getServiceId() {
    return serviceId;
  }

  public void setServiceId(long serviceId) {
    this.serviceId = serviceId;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
