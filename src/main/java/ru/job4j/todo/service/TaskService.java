package ru.job4j.todo.service;

import ru.job4j.todo.dto.TaskDTO;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    void deleteById(int id);

    void update(Task task);

    Optional<Task> findById(int id);

    Collection<TaskDTO> findAll();

    Collection<Task> findAllTasksByExecutingStatus(boolean flag);

    void setTaskExecutedById(int id);
}
