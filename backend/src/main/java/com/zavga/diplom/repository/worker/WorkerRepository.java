package com.zavga.diplom.repository.worker;

import com.zavga.diplom.entity.worker.Worker;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker,Long> {

    @EntityGraph(attributePaths = {"works"})
    @Query("select distinct w from Worker w")
    List<Worker> findAll();

    @EntityGraph(attributePaths = {"works"})
    Optional<Worker> findById(Long id);

    @EntityGraph(attributePaths = {"works"})
    List<Worker> findAllById(Iterable<Long> ids);


    @Modifying
    @Query("delete from Worker w where w.id = :id")
    int deleteByIdWithCount(@Param("id") Long id);
}
