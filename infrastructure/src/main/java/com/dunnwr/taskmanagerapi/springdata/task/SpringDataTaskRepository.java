package com.dunnwr.taskmanagerapi.springdata.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataTaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByUserId(Long userId);
    Optional<TaskEntity> findByIdAndUserId(Long id, Long userId);
}
