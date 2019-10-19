package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal MEAL_100002 = new Meal(MEAL_ID, LocalDateTime.of(2019, Month.OCTOBER, 19, 7, 0), "breakfast", 700);
    public static final Meal MEAL_100003 = new Meal(MEAL_ID+1, LocalDateTime.of(2019, Month.OCTOBER, 19, 13, 0), "dinner", 800);
    public static final Meal MEAL_100004 = new Meal(MEAL_ID+2, LocalDateTime.of(2019, Month.OCTOBER, 19, 19, 0), "supper", 500);
    public static final Meal MEAL_100005 = new Meal(MEAL_ID+3, LocalDateTime.of(2019, Month.OCTOBER, 20, 8, 0), "breakfast", 700);
    public static final Meal MEAL_100006 = new Meal(MEAL_ID+4, LocalDateTime.of(2019, Month.OCTOBER, 20, 12, 0), "lunch", 900);
    public static final Meal MEAL_100007 = new Meal(MEAL_ID+5, LocalDateTime.of(2019, Month.OCTOBER, 20, 20, 0), "dinner", 800);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
