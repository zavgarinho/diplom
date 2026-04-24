package com.zavga.diplom.repository.work;

import com.zavga.diplom.entity.work.InstallationWork;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface InstallationWorkRepository extends JpaRepository<InstallationWork,Long> {

    @EntityGraph(attributePaths = {"workers","object"})
    @Query("select distinct iw from InstallationWork iw")
    List<InstallationWork> findAll();

    @EntityGraph(attributePaths = {"workers","object"})
    Optional<InstallationWork> findById(Long id);

    @EntityGraph(attributePaths = {"workers","object"})
    List<InstallationWork> findAllById(Iterable<Long> ids);

    @Modifying
    @Query("delete from InstallationWork iw where iw.id = :id")
    int deleteByIdWithCount(@Param("id") Long id);
}
