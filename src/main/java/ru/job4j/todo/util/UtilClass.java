package ru.job4j.todo.util;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.ZoneId;
import java.util.Collection;
import java.util.TimeZone;

public class UtilClass {

    public static void setTimeZoneOfTasksAccUserZone(HttpServletRequest request, Collection<Task> tasks) {
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
}
