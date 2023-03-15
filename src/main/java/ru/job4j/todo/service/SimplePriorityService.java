package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.Collection;
import java.util.List;

@ThreadSafe
@Service
public class SimplePriorityService implements PriorityService {

    private final PriorityRepository priorityRepository;

    public SimplePriorityService(PriorityRepository hibPriorityRepository) {
        this.priorityRepository = hibPriorityRepository;
    }

    @Override
    public Collection<Priority> findAll() {
        return (List<Priority>) priorityRepository.findAll();
    }
}
