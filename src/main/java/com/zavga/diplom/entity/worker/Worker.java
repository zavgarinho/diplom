package com.zavga.diplom.entity.worker;


import com.zavga.diplom.entity.work.InstallationWork;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workers")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "workers_seq")
    @SequenceGenerator(
            name = "workers_seq",
            sequenceName = "workers_id_seq",
            allocationSize = 1
    )
    private Long id;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "specialization",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WorkerSpecialization specialization;

    @ManyToMany(mappedBy = "workers")
    private Set<InstallationWork> works;


}
