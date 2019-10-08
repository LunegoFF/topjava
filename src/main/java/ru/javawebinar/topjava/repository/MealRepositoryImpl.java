package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealRepositoryImpl implements MealRepository {
    private static final Logger log = getLogger(MealRepositoryImpl.class);


    private List<Meal> mealList = Collections.synchronizedList(new ArrayList<>());

    public MealRepositoryImpl() {

        mealList.add(new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealList.add(new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealList.add(new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealList.add(new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealList.add(new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealList.add(new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }

    @Override
    public List<MealTo> getAllMeals() {
        log.debug("Get mealList, size=" + mealList.size());
        return MealsUtil.getFiltered(mealList, LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    @Override
    public void addMeal(Meal meal) {
        log.debug("Add meal to DB " + meal.toString());
        mealList.add(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        log.debug("Delete meal from DB with ID " + mealId);
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId() == mealId && mealList.size() > 0) {
                log.debug("delete ID " + mealId);
                mealList.remove(i);
            }
        }
    }

    @Override
    public Meal getMealById(int mealId) {
        log.debug("Get meal from DB with ID " + mealId);
        for (Meal meal : mealList) {
            if (meal.getId() == mealId){
                log.debug("return " + meal.toString());
                return meal;
            }
        }
        return null;
    }

    @Override
    public void update(Meal meal) {
        log.debug("update " + meal.toString());
        for (int i = 0; i < mealList.size(); i++) {
            if (mealList.get(i).getId() == meal.getId()) {
                log.debug("get ID " + meal.toString());
                mealList.set(i, meal);
            }
        }
    }
}