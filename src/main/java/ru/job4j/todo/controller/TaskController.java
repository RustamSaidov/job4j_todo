package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;


@ThreadSafe
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/active")
    public String getActiveTasks(Model model) {
        model.addAttribute("active", taskService.findAllTasksByExecutingStatus(false));
        return "tasks/active";
    }

    @GetMapping("/completed")
    public String getCompletedTasks(Model model) {
        model.addAttribute("completed", taskService.findAllTasksByExecutingStatus(true));
        return "tasks/completed";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/one";
    }

    @GetMapping("/update_task/{id}")
    public String updateById(Model model, @PathVariable int id) {
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
//        return "redirect:/tasks";
        return "tasks/update_task";
    }

    @PostMapping("/update_task")
    public String update(@ModelAttribute Task task, Model model) {
        try {
            taskService.update(task);
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @PostMapping("/execute_task")
    public String makeTaskExecuted(@ModelAttribute Task task, Model model) {
        try {
            taskService.setTaskExecutedById(task.getId());
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @PostMapping("/delete_task")
    public String deleteTask(@ModelAttribute Task task, Model model) {
        try {
            taskService.deleteById(task.getId());
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.save(task);
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
}
