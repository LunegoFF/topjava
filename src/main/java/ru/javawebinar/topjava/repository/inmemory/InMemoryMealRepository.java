package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private LocalDate startDate = LocalDate.MIN;
    private LocalDate endDate = LocalDate.MAX;
    private LocalTime startTime = LocalTime.MIN;
    private LocalTime endTime = LocalTime.MAX;

    {
        MealsUtil.MEALS.forEach(meal -> this.save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        return meal.getUserId() == userId ? meal: null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll with userId {}", userId);
        return repository.values()
                .stream()
                .filter(meal -> userId == meal.getUserId())
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllWithFilter(int userId, String startDate, String endDate, String startTime, String endTime) {
        log.info("getAll with filter of user {}", userId);
        if (!startDate.equals(""))
            this.startDate = LocalDate.parse(startDate);
        if (!endDate.equals(""))
            this.endDate = LocalDate.parse(endDate);
        if (!startTime.equals(""))
            this.startTime = LocalTime.parse(startTime);
        if (!endTime.equals(""))
            this.endTime = LocalTime.parse(endTime);


        return repository.values()
                .stream()
                .peek(meal -> log.info("meal: {}", meal))
                .filter(meal ->
                        userId == meal.getUserId() &&
                        this.startDate.isBefore(meal.getDate()) &&
                                this.endDate.isAfter(meal.getDate()) &&
                                this.startTime.isBefore(meal.getTime()) &&
                                this.endTime.isAfter(meal.getTime()))
                .peek(meal -> log.info("meal: {}", meal))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

