package com.zavga.diplom.entity.equipment;


import com.zavga.diplom.entity.object.SecurityObject;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "equipment_seq")
    @SequenceGenerator(
            name = "equipment_seq",
            sequenceName = "equipment_id_seq",
            allocationSize = 1
    )
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "manufacturer",nullable = false)
    private String manufacturer;
    @Column(name = "equipment_type",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EquipmentType type;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private SecurityObject object;
}
