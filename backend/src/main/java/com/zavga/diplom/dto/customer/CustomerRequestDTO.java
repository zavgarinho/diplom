package com.zavga.diplom.dto.customer;

import com.zavga.diplom.entity.customer.CustomerType;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;


public record CustomerRequestDTO(
        String firstName,
        String lastName,
        String patronymic,
        @Email(message = "Неправильний формат пошти")
        String email,
        CustomerType type
) {

}
