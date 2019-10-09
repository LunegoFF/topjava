package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Map;

public interface MealRepository {

    Map<Integer, Meal> getAll();
    Meal create(Meal meal);
    void delete(int mealId);
    Meal getById(int mealId);
    Meal update(int id, Meal meal);
}
