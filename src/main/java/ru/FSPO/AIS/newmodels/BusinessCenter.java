package ru.FSPO.AIS.newmodels;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "business_center")
@Data
public class BusinessCenter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bc_id")
  private Long bcId;

  @Size(min = 12, max= 100,message = "Name should be valid")
  private String name;
  @Size(min = 8, max= 100,message = "Short name should be valid")
  @Column(name = "short_name")
  private String shortName;
  @Size(min = 15, max= 100,message = "Address should be valid")
  private String address;
  @Column(name = "image_path")
  private String imagePath;
  @ManyToOne
  @JoinColumn(name = "bc_link_id")
  private BcLink bcLink;
  @OneToMany(mappedBy = "businessCenter")
  private Set<Floor> floors;



}
