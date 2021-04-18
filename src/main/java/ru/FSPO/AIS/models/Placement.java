package ru.FSPO.AIS.models;


public class Placement {

  private long placementId;
  private long surface;
  private long price;
  private long floorId;
  private String imagePath;


  public long getPlacementId() {
    return placementId;
  }

  public void setPlacementId(long placementId) {
    this.placementId = placementId;
  }


  public long getSurface() {
    return surface;
  }

  public void setSurface(long surface) {
    this.surface = surface;
  }


  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }


  public long getFloorId() {
    return floorId;
  }

  public void setFloorId(long floorId) {
    this.floorId = floorId;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  @Override
  public String toString() {
    return "Placement{" +
            "placementId=" + placementId +
            ", surface=" + surface +
            ", price=" + price +
            ", floorId=" + floorId +
            ", imagePath='" + imagePath + '\'' +
            '}';
  }
}
