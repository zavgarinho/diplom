package com.zavga.diplom.entity.work;


import com.zavga.diplom.entity.object.SecurityObject;
import com.zavga.diplom.entity.worker.Worker;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "installation_works")
public class InstallationWork {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "work_seq")
    @SequenceGenerator(
            name = "work_seq",
            sequenceName = "work_id_seq",
            allocationSize = 1
    )
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private InstallationWorkStatus status;

    @ManyToMany
    @JoinTable(
          name = "work_worker",
          joinColumns = {@JoinColumn(name = "work_id")},
          inverseJoinColumns = {@JoinColumn(name = "worker_id")}
    )
    private Set<Worker> workers;

    @ManyToOne
    @JoinColumn(name = "object_id")
    private SecurityObject object;


}
