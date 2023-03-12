package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    /**
     * Сохранить в базе.
     *
     * @param user пользователь.
     * @return Optional of user.
     */
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
        } catch (Exception exception) {
            return Optional.empty();
        }
        return Optional.ofNullable(user);
    }

    /**
     * Найти пользователя по email и password.
     *
     * @param email    email.
     * @param password password.
     * @return Optional of user.
     */
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> result = Optional.empty();
        try {
            result = crudRepository.optional("""
                             from User as u
                             WHERE u.email = :fEmail AND u.password = :fPassword
                            """, User.class,
                    Map.of("fEmail", email, "fPassword", password));
        } catch (Exception exception) {
            return result;
        }
        return result;
    }

    /**
     * Найти пользователя по id.
     *
     * @param id ID.
     * @return Optional of user.
     */
    @Override
    public Optional<User> findById(int id) {
        Optional<User> result = Optional.empty();
        try {
            result = crudRepository.optional("""
                             from User as u WHERE u.id = :fId
                            """, User.class,
                    Map.of("fId", id));
        } catch (Exception exception) {
            return result;
        }
        return result;
    }
}
