package com.zavga.diplom.dto.customer;

import com.zavga.diplom.entity.customer.CustomerType;
import com.zavga.diplom.entity.object.SecurityObject;

import java.util.List;

public record CustomerShortDTO (
        Long id,
        String firstName,
        String lastName,
        String patronymic,
        String email,
        CustomerType type
){
}
