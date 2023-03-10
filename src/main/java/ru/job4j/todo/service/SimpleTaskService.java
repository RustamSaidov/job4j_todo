package ru.job4j.todo.service;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleTaskService implements TaskService {

    private final TaskRepository taskRepository;

    public SimpleTaskService(TaskRepository hibTaskRepository) {
        this.taskRepository = hibTaskRepository;
    }


    @Override
    public Task save(Task task) {
        Optional<Task> result = taskRepository.save(task);
        if (result.isEmpty()) {
            throw new NoSuchElementException("Task does not saved");
        }
        return result.get();
    }

    @Override
    public void deleteById(int id) {
        boolean result = taskRepository.deleteById(id);
        if (!result) {
            throw new NoSuchElementException("Task with this id is not found");
        }
    }

    @Override
    public void update(Task task) {
        boolean result = taskRepository.update(task);
        if (!result) {
            throw new NoSuchElementException("Task with this id is not found");
        }
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAllOrderById();
    }

    @Override
    public Collection<Task> findAllTasksByExecutingStatus(boolean flag) {
        return taskRepository.findAllTasksByExecutingStatus(flag);
    }

    @Override
    public void setTaskExecutedById(int id) {
        taskRepository.setTaskExecutedById(id);
        boolean result = taskRepository.setTaskExecutedById(id);
        if (!result) {
            throw new NoSuchElementException("Task with this id is not found");
        }
    }
}
