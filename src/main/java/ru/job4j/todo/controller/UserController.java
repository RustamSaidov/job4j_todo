package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*Оттестено*/
    @GetMapping("/register")
    public String getRegistationPage() {
        return "users/register";
    }

    /*Оттестено*/
    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        var savedUser = userService.save(user);
        if (savedUser==null) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }

//    @PostMapping("/register")
//    public String register(Model model, @ModelAttribute User user) {
//        try {
//            userService.save(user);
//            return "redirect:/tasks";
//        } catch (Exception exception) {
//            model.addAttribute("message", exception.getMessage());
//            return "errors/404";
//        }
//    }



    /*Оттестено*/
    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    /*Оттестено*/
//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
//        var userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
//        if (userOptional.isEmpty()) {
//            model.addAttribute("error", "Почта или пароль введены неверно");
//            return "users/login";
//        }
//        var session = request.getSession();
//        session.setAttribute("user", userOptional.get());
//        return "redirect:/vacancies";
//    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        try {
            var userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
            var session = request.getSession();
            session.setAttribute("user", userOptional.get());
            return "redirect:/tasks";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    /*Оттестено*/
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
