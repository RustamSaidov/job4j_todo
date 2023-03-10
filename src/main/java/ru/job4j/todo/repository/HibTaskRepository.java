package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param task пользователь.
     * @return Optional of task.
     */
    public Optional<Task> save(Task task) {
        try {
            crudRepository.run(session -> session.persist(task));
        } catch (Exception exception) {
            return Optional.empty();
        }
        return Optional.ofNullable(task);
    }

    /**
     * Удалить пользователя по id.
     *
     * @return boolean.
     */
    public boolean deleteById(int taskId) {
        try {
            crudRepository.run(
                    "delete from Task where id = :fId",
                    Map.of("fId", taskId));
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param task пользователь.
     * @return boolean.
     */
    public boolean update(Task task) {
        try {
            crudRepository.run(session -> session.merge(task));
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

    /**
     * Найти пользователя по id.
     *
     * @param id ID.
     * @return Optional of user.
     */
    @Override
    public Optional<Task> findById(int id) {
        Optional<Task> result = Optional.empty();
        try {
            result = crudRepository.optional("""
                             from Task as t WHERE t.id = :fId
                            """, Task.class,
                    Map.of("fId", id));
        } catch (Exception exception) {
            return result;
        }
        return result;
    }

    /**
     * Список пользователь отсортированных по id.
     *
     * @return список пользователей.
     */
    @Override
    public Collection<Task> findAllOrderById() {
        return crudRepository.query("from Task order by id asc", Task.class);
    }

    /**
     * Список активных/выполненных задач.
     *
     * @param flag флаг.
     * @return список задач.
     */
    @Override
    public Collection<Task> findAllTasksByExecutingStatus(boolean flag) {
        return crudRepository.query(String.format("from Task WHERE done is %s ORDER BY id", flag), Task.class);
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param id ID.
     * @return boolean.
     */
    @Override
    public boolean setTaskExecutedById(int id) {

        try {
            crudRepository.run(
                    "UPDATE Task SET done = :fDone where id = :fId",
                    Map.of("fId", id, "fDone", true));
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
