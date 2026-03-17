package com.dunnwr.taskmanagerapi.springdata.task;

import com.dunnwr.taskmanagerapi.models.task.Priority;
import com.dunnwr.taskmanagerapi.models.task.Status;
import com.dunnwr.taskmanagerapi.models.task.Task;
import com.dunnwr.taskmanagerapi.repositories.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<Task> findAll(){
        return springDataTaskRepository.findAll().stream()
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
}
