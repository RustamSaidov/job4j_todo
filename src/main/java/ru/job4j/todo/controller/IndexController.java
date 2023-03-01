package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ThreadSafe
@Controller
@RequestMapping("/")
/*@RestController*/
public class IndexController {

    /*Оттестено*/
    @GetMapping({"/", "/index"})
    public String getIndex() {
        return "/index";
    }
}
