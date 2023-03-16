package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HibGategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        var list = crudRepository.query("FROM Category", Category.class);
        return list;
    }

//    @Override
//    public Optional<List<Category>> findByTaskId(int id) {
//
//    }
}
