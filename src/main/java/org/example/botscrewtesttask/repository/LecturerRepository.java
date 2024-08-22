package org.example.botscrewtesttask.repository;

import org.example.botscrewtesttask.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("SELECT CONCAT(l.name,' ', l.surname) from Lecturer l " +
            "where LOWER(CONCAT(l.name,' ', l.surname) ) " +
            "like LOWER(CONCAT('%',:template,'%'))")
    List<String> findByNameTemplate(@Param("template") String template);
}
