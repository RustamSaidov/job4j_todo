package ru.job4j.todo.service;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleTaskService implements TaskService{

    private final TaskStore taskStore;

    public SimpleTaskService(TaskStore hibTaskStore) {
        this.taskStore = hibTaskStore;
    }


    @Override
    public Task save(Task task) {
        return taskStore.save(task);
    }

    @Override
    public void deleteById(int id) {
        taskStore.deleteById(id);
    }

    @Override
    public void update(Task task) {
        taskStore.update(task);
    }

    @Override
    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    @Override
    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public Collection<Task> findAllActiveTasks() {
        return taskStore.findAllActiveTasks();
    }

    @Override
    public Collection<Task> findAllCompletedTasks() {
        return taskStore.findAllCompletedTasks();
    }
}
