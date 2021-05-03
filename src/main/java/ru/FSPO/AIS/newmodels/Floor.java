package ru.FSPO.AIS.newmodels;

import lombok.Data;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "floor_id")
    private Long floorId;
    @Min(value = -2, message = "Этаж не может быть отрицательным")
    @Column(name = "floor_number")
    private long floorNumber;
    @NotEmpty(message = "Описание не должно быть пустым")
    private String description;
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne
    @JoinColumn(name = "business_center")
    private BusinessCenter businessCenter;
    @OneToMany(mappedBy = "floor")
    private Set<Placement> placements;

}
