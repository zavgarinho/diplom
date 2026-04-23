package com.zavga.diplom.entity.customer;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.zavga.diplom.entity.object.SecurityObject;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name="customer_seq",sequenceName = "customer_id_seq",allocationSize = 1)
    private Long id;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;
    @Column(name = "patronymic",nullable = false)
    private String patronymic;
    @Column(name = "email",nullable = false)
    private String email;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "customer_type",nullable = false, length = 15)
    private CustomerType type;

    @OneToMany(mappedBy = "customer")
    private List<SecurityObject> objects;

}
