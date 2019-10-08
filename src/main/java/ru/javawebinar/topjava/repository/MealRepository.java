package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealRepository {
    List<MealTo> getAllMeals();
    void addMeal(Meal meal);
    void deleteMeal(int mealId);
    Meal getMealById(int mealId);
    void update(Meal meal);
}
