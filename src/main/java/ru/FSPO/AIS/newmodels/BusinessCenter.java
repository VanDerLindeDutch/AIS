package ru.FSPO.AIS.newmodels;


import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class BusinessCenter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long bcId;

  @Size(min = 12, max= 100,message = "Name should be valid")
  private String name;
  @Size(min = 8, max= 100,message = "Short name should be valid")
  private String shortName;
  @Size(min = 15, max= 100,message = "Address should be valid")
  private String address;
  private String imagePath;
  @ManyToOne
  private BcLink bcLink;


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

  public BcLink getBcLink() {
    return bcLink;
  }

  public void setBcLink(BcLink bcLink) {
    this.bcLink = bcLink;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }


}
