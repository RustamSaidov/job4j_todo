package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;


@ThreadSafe
@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    public TaskController(TaskService taskService, PriorityService priorityService, CategoryService categoryService) {
        this.taskService = taskService;
        this.priorityService = priorityService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getAll(Model model, HttpServletRequest request) {
        var tasks = taskService.findAll();
        setTimeZoneOfTasksAccUserZone(request, tasks);
        model.addAttribute("tasks", tasks);
        return "tasks/list";
    }

    @GetMapping("/active")
    public String getActiveTasks(Model model, HttpServletRequest request) {
        var tasks = taskService.findAllTasksByExecutingStatus(false);
        setTimeZoneOfTasksAccUserZone(request, tasks);
        model.addAttribute("active", tasks);
        return "tasks/active";
    }

    @GetMapping("/completed")
    public String getCompletedTasks(Model model, HttpServletRequest request) {
        var tasks = taskService.findAllTasksByExecutingStatus(true);
        setTimeZoneOfTasksAccUserZone(request, tasks);
        model.addAttribute("completed", tasks);
        return "tasks/completed";
    }

    private void setTimeZoneOfTasksAccUserZone(HttpServletRequest request, Collection<Task> tasks) {
        User user = (User) request.getSession().getAttribute("user");
        for (Task task : tasks) {
            if (user.getUserzone() == null) {
                user.setUserzone(TimeZone.getDefault().getID());
            }
            var time = task.getCreated()
                    .atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of(user.getUserzone())).toLocalDateTime();
            task.setCreated(time);
        }
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
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model, HttpServletRequest request, @RequestParam(value = "category.id") List<Integer> catIDs) {
        List<Category> categories = (List<Category>) categoryService.findAllByIdList(catIDs);
        try {
            User user = (User) request.getSession().getAttribute("user");
            task.setUser(user);
            task.setCategories(categories);
            taskService.save(task);
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/update_task/{id}")
    public String updateById(Model model, @PathVariable int id) {
        model.addAttribute("priorities", priorityService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        var taskOptional = taskService.findById(id);
        if (taskOptional.isEmpty()) {
            model.addAttribute("message", "Задание с указанным идентификатором не найдено");
            return "errors/404";
        }
        model.addAttribute("task", taskOptional.get());
        return "tasks/update_task";
    }

    @PostMapping("/update_task")
    public String update(@ModelAttribute Task task, Model model, HttpServletRequest request, @RequestParam(value = "category.id") List<Integer> catIDs) {
        List<Category> categories = (List<Category>) categoryService.findAllByIdList(catIDs);
        try {
            User user = (User) request.getSession().getAttribute("user");
            task.setUser(user);
            task.setCategories(categories);
            taskService.update(task);
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }
}
