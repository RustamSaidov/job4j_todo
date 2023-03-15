package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibPriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Priority> findAll() {
        var list = crudRepository.query("FROM Priority", Priority.class);
        return list;
    }
}
