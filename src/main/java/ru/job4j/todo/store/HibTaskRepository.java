package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibTaskRepository implements TaskRepository {
    private final SessionFactory sf;


    @Override
    public Task save(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return task;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        int result=0;
        try {
            session.beginTransaction();
            result = session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
        return result > 0;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        int result=0;
        try {
            session.beginTransaction();
            result = session.createQuery("""
                            UPDATE Task
                            SET description = :description, created = :created,
                                done = :done
                            WHERE id = :id
                            """)
                    .setParameter("description", task.getDescription())
                    .setParameter("created", task.getCreated())
                    .setParameter("done", task.isDone())
                    .setParameter("id", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result > 0;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task result = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(result);
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from Task ORDER BY id", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

        @Override
    public Collection<Task> findAllTasksByExecutingStatus(boolean flag) {
        Session session = sf.openSession();
        session.beginTransaction();
        String query = String.format("from Task WHERE done is %s ORDER BY id", flag);
        List<Task> result = session.createQuery(query, Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public boolean setTaskExecutedById(int id) {
        Session session = sf.openSession();
        int result=0;
        try {
            session.beginTransaction();
            result = session.createQuery("""
                            UPDATE Task
                            SET done = :done
                            WHERE id = :id
                            """)
                    .setParameter("done", true)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return result > 0;
    }
}