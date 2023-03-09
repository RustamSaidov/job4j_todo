package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibUserRepository implements UserRepository {

    private final SessionFactory sf;


//    session.beginTransaction();
//    SQLQuery sqlQuery = session.createSQLQuery
//            ("insert into myobj(id,name) values(?,?)");
//    sqlQuery.setLong(0, id);
//    sqlQuery.setString(1, name);
//    sqlQuery.executeUpdate();
//    session.getTransaction().commit();


    @Override
    public User save(User user) {
        var savedUser = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            var result = session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        session.close();
//        System.out.println("USER FROM CONTROLLER: "+ user);
        return user;
    }


//    @Override
//    public Optional<User> save(User user) {
//        Session session = sf.openSession();
//        Optional<User> result = Optional.empty();
//        try {
//            session.beginTransaction();
//            result = session.createQuery("""
//                            "insert into User (email, name, password) select u.email, u.name, u.password from User u"
//                            """)
//                    .setParameter("email", user.getEmail())
//                    .setParameter("name", user.getName())
//                    .setParameter("password", user.getPassword())
//                    .uniqueResultOptional();
//            session.getTransaction().commit();
//            session.close();
//
//            System.out.println("**********************************");
//            System.out.println(result);
//            System.out.println("**********************************");
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//        }
//        session.close();
//        return result.map(obj -> (User) obj);
//    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = sf.openSession();
        Optional<User> result = Optional.empty();
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
