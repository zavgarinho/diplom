package com.zavga.diplom.repository.object;

import com.zavga.diplom.entity.object.SecurityObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityObjectRepository extends JpaRepository<SecurityObject,Long> {
}
