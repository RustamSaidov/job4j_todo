package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;


@ThreadSafe
@Service
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    public SimpleUserService(UserRepository HibUserRepository) {
        this.userRepository = HibUserRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);


//        var savedUser = userRepository.save(user);
//        if (savedUser ==null) {
//            throw new NoSuchElementException("Пользователь с таким почтовым адресом уже существует");
//        }
//        return savedUser;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        Optional<User> foundedUser = userRepository.findByEmailAndPassword(email, password);
        if (foundedUser.isEmpty()) {
            throw new NoSuchElementException("Пользователь с такой почтой и паролем не найден");
        }
        return foundedUser;
    }
}
