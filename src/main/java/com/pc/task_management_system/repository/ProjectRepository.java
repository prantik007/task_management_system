package com.pc.task_management_system.repository;

import com.pc.task_management_system.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p where p.user.username = :username")
    List<Project> findAllByUsername(@Param("username") String username);
}
