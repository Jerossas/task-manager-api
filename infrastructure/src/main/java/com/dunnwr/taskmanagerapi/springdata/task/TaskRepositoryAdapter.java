package com.dunnwr.taskmanagerapi.springdata.task;

import com.dunnwr.taskmanagerapi.models.task.Priority;
import com.dunnwr.taskmanagerapi.models.task.Status;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepositoryAdapter implements TaskRepository {

    private final SpringDataTaskRepository springDataTaskRepository;

    public TaskRepositoryAdapter(SpringDataTaskRepository springDataTaskRepository){
        this.springDataTaskRepository = springDataTaskRepository;
    }

    @Override
    public Task save(Task task) {

        TaskEntity newTask = new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getOwnerId(),
                task.getDueDate()
        );

        TaskEntity savedEntity = springDataTaskRepository.save(newTask);

        return Task.restore(
                savedEntity.getId(),
                savedEntity.getTitle(),
                savedEntity.getDescription(),
                Status.from(savedEntity.getStatus()),
                Priority.from(savedEntity.getPriority()),
                savedEntity.getUserId(),
                savedEntity.getDueDate()
        );
    }

    @Override
    public List<Task> findByUserId(Long userId){
        return springDataTaskRepository.findByUserId(userId).stream()
                .map(entity -> Task.restore(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getDescription(),
                        Status.from(entity.getStatus()),
                        Priority.from(entity.getPriority()),
                        entity.getUserId(),
                        entity.getDueDate()
                )).toList();
    }

    @Override
    public Optional<Task> findByIdAndUserId(Long taskId, Long userId) {
        return springDataTaskRepository.findByIdAndUserId(taskId, userId)
                .map(entity -> Task.restore(
                        entity.getId(),
                        entity.getTitle(),
                        entity.getDescription(),
                        Status.from(entity.getStatus()),
                        Priority.from(entity.getPriority()),
                        entity.getUserId(),
                        entity.getDueDate()
                ));
    }

    @Override
    public void deleteById(Long taskId) {
        springDataTaskRepository.deleteById(taskId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        springDataTaskRepository.deleteByUserId(userId);
    }
}
