package com.zavga.diplom.repository.equipment;

import com.zavga.diplom.entity.equipment.Equipment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
    @EntityGraph(attributePaths = {"object.customer"})
    List<Equipment> findAll();
    @EntityGraph(attributePaths = {"object.customer"})
    Optional<Equipment> findById(Long id);

    @Modifying
    @Query("delete from Equipment e where e.id = :id")
    int deleteByIdWithCount(@Param("id") Long id);
}
