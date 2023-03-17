package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

@Repository
@AllArgsConstructor
public class HibGategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        var list = crudRepository.query("FROM Category", Category.class);
        return list;
    }

    @Override
    public Collection<Category> findAllByIdList(List<Integer> catIds) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        for (int i = 0; i < catIds.size(); i++) {
            stringJoiner.add(catIds.get(i).toString());
        }
        String query = String.format("FROM Category WHERE id IN (%s)", stringJoiner);
        var list = crudRepository.query(query, Category.class);
        return list;
    }
}
