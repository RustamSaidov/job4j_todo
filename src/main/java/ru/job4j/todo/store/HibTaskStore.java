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
public class HibTaskStore implements TaskStore{
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
    public void deleteById(int id) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
    }

    @Override
    public void update(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("""
                    UPDATE Task
                    SET description = :description, created = :created,
                        done = :done
                    WHERE id = :id
                    """)
                    .setParameter("description", task.getDescription())
                    .setParameter("created", task.getCreated())
                    .setParameter("done", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
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
    public Collection<Task> findAllActiveTasks() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from Task WHERE done is false ORDER BY id", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Collection<Task> findAllCompletedTasks() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> result = session.createQuery("from Task WHERE done is true ORDER BY id", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
