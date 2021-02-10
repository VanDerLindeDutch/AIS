package ru.FSPO.AIS.models;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Floor {

  private long floorId;
  @Min(value = -2, message = "Этаж не может быть отрицательным")
  private long floorNumber;
  private long bcId;
  @NotEmpty(message = "Описание не должно быть пустым")
  private String description;
  private String imagePath;


  public long getFloorId() {
    return floorId;
  }

  public void setFloorId(long floorId) {
    this.floorId = floorId;
  }


  public long getFloorNumber() {
    return floorNumber;
  }

  public void setFloorNumber(long floorNumber) {
    this.floorNumber = floorNumber;
  }


  public long getBcId() {
    return bcId;
  }

  public void setBcId(long bcId) {
    this.bcId = bcId;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
}
