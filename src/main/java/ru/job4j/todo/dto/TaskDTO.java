package ru.job4j.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private int id;
    private String description;
    private LocalDateTime created = LocalDateTime.now();
    private boolean done;
    private User user;
    private Priority priority;
    private String categories;


}
