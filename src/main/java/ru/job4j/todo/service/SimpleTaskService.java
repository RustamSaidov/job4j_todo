package ru.job4j.todo.service;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.dto.TaskDTO;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.TaskRepository;

import java.util.*;

@ThreadSafe
@Service
public class SimpleTaskService implements TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    public SimpleTaskService(TaskRepository hibTaskRepository, CategoryService categoryService) {
        this.taskRepository = hibTaskRepository;
        this.categoryService = categoryService;
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
    public Collection<TaskDTO> findAll() {
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        tasks.stream().forEach(x -> System.out.println("SERVICE: " + x));
        Collection<TaskDTO> tasksDTO = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            TaskDTO taskDTO = new TaskDTO(
                    tasks.get(i).getId(),
                    tasks.get(i).getDescription(),
                    tasks.get(i).getCreated(),
                    tasks.get(i).isDone(),
                    tasks.get(i).getUser(),
                    tasks.get(i).getPriority(),
                    getStringFromCollection(tasks.get(i))
            );
            tasksDTO.add(taskDTO);
        }
        tasksDTO.stream().forEach(x -> System.out.println("SERVICE DTO: " + x));
        return tasksDTO;
    }

    private String getStringFromCollection(Task task) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (int i = 0; i < task.getCategories().size(); i++) {
            stringJoiner.add(task.getCategories().get(i).getName());
        }
        return stringJoiner.toString();
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
