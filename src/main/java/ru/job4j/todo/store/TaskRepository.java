package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    boolean deleteById(int id);

    boolean update(Task task);

    Optional<Task> findById(int id);

    Collection<Task> findAll();

    Collection<Task> findAllTasksByExecutingStatus(boolean flag);

    boolean setTaskExecutedById(int id);
}