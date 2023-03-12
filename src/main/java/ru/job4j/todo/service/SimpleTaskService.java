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
    private final UserService userService;

    public SimpleTaskService(TaskRepository hibTaskRepository, UserService userService) {
        this.taskRepository = hibTaskRepository;
        this.userService = userService;
    }


    @Override
    public Task save(Task task) {
        Task task = new Task(taskDTO.getId(), taskDTO.getDescription(), taskDTO.getCreated(), taskDTO.isDone(), taskDTO.);
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
        List<Task> tasks = (List<Task>) taskRepository.findAllOrderById();
        List<TaskDTO> listOfTaskDTO = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            listOfTaskDTO.add(new TaskDTO(tasks.get(i).getId(),
                    tasks.get(i).getDescription(),
                    tasks.get(i).getCreated(), tasks.get(i).isDone(), userService.findById(tasks.get(i).getUser().getId()).get().getName()));
        }
        return listOfTaskDTO;
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
