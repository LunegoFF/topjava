package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

public class MealRepositoryRAMImpl implements MealRepository {
    private static final Logger log = getLogger(MealRepositoryRAMImpl.class);

    private Map<Integer, Meal> meals = Collections.synchronizedSortedMap(new TreeMap<>());

    public MealRepositoryRAMImpl() {

        meals.put(1, new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 29, 10, 0), "Завтрак", 500));
        meals.put(2, new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 29, 13, 0), "Обед", 1000));
        meals.put(3, new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 29, 20, 0), "Ужин", 500));
        meals.put(4, new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 30, 10, 0), "Завтрак", 1000));
        meals.put(5, new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 30, 13, 0), "Обед", 500));
        meals.put(6, new Meal(LocalDateTime.of(2019, Month.SEPTEMBER, 30, 20, 0), "Ужин", 510));

    }

    @Override
    public Map<Integer, Meal> getAll() {
        log.debug("Get mealList, size=" + meals.size());
        return meals;
    }

    @Override
    public Meal create(Meal meal) {
        log.debug("Add meal to DB " + meal);
        meals.put(maxID() + 1, meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        log.debug("Delete meal from DB with ID " + id);
        meals.remove(id);
    }

    @Override
    public Meal getById(int id) {
        log.debug("Get meal from DB with ID " + id);
        return meals.get(id);
    }

    @Override
    public Meal update(int id, Meal meal) {
        log.debug("update meal " + meal.toString() + " with id=" + id);
        meals.put(id, meal);
        return null;
    }

    private int maxID(){
        return new TreeSet<>(meals.keySet()).last();
    }
}