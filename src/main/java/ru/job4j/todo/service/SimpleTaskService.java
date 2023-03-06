package ru.job4j.todo.service;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskRepository;

import java.util.Collection;
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
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void update(Task task) {
        taskRepository.update(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Collection<Task> findAllActiveTasks() {
        return taskRepository.findAllActiveTasks();
    }

    @Override
    public Collection<Task> findAllCompletedTasks() {
        return taskRepository.findAllCompletedTasks();
    }

    @Override
    public void setTaskExecutedById(int id) {
        taskRepository.setTaskExecutedById(id);
    }
}
