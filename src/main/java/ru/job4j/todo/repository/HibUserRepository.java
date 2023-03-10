package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibUserRepository implements UserRepository {

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Optional<User> savedUser = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            savedUser = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return savedUser;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = sf.openSession();
        Optional result = Optional.empty();
        try {
            session.beginTransaction();
            result = session.createQuery("""
                            from User as u
                            WHERE u.email = :email AND u.password = :password
                            """)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result;

    }

    @Override
    public Collection<User> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<User> result = session.createQuery("from User ORDER BY id", User.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        int result = 0;
        try {
            session.beginTransaction();
            result = session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return result > 0;
    }
}
