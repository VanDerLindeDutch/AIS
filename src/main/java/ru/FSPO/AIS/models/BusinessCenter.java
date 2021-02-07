package ru.FSPO.AIS.models;


import javax.validation.constraints.Size;

public class BusinessCenter {

  private long bcId;

  @Size(min = 12, max= 100,message = "Name should be valid")
  private String name;
  @Size(min = 8, max= 100,message = "Short name should be valid")
  private String shortName;
  @Size(min = 15, max= 100,message = "Address should be valid")
  private String address;
  private long bcLinkId;
  private String imagePath;


  public long getBcId() {
    return bcId;
  }

  public void setBcId(long bcId) {
    this.bcId = bcId;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }


  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }


  public long getBcLinkId() {
    return bcLinkId;
  }

  public void setBcLinkId(long bcLinkId) {
    this.bcLinkId = bcLinkId;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  @Override
  public String toString() {
    return "BusinessCenter{" +
            "bcId=" + bcId +
            ", name='" + name + '\'' +
            ", shortName='" + shortName + '\'' +
            ", address='" + address + '\'' +
            ", bcLinkId=" + bcLinkId +
            ", imagePath='" + imagePath + '\'' +
            '}';
  }
}
