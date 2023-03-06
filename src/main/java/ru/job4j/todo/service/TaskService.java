package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Task save(Task task);

    void deleteById(int id);

    void update(Task task);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findAllActiveTasks();

    Collection<Task> findAllCompletedTasks();

    void setTaskExecutedById(int id);
}
