package com.zavga.diplom.entity.object;


import com.zavga.diplom.entity.customer.Customer;
import com.zavga.diplom.entity.equipment.Equipment;
import com.zavga.diplom.entity.work.InstallationWork;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "objects")
@NoArgsConstructor
@AllArgsConstructor
public class SecurityObject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "objects_seq")
    @SequenceGenerator(
           name = "objects_seq",
           sequenceName = "objects_id_seq",
            allocationSize = 1
    )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "area", nullable = false)
    private Double area;

    @Column(name = "floor",nullable = false)
    private Integer floor;

    @Column(name = "object_type",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ObjectType type;

    @Column(name = "object_status")
    @Enumerated(value = EnumType.STRING)
    private ObjectStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "object")
    private Set<Equipment> equipment;

    @OneToMany(mappedBy = "object")
    private Set<InstallationWork> works;

}
