package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;


    @Test
    public void get() {
        Meal meal = service.get(100002, USER_ID);
        assertMatch(meal, MEAL_USER_100002);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWithWrongUser() throws Exception {
        service.get(100003, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_USER_100004, MEAL_USER_100003);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedWithWrongUser() throws Exception {
        service.delete(100002, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        List<Meal> getBetween = service.getBetweenDates(LocalDate.of(2019, Month.OCTOBER, 19), LocalDate.of(2019, Month.OCTOBER, 19), USER_ID);
        assertMatch(getBetween, MEAL_USER_100004, MEAL_USER_100003, MEAL_USER_100002);
    }

    @Test
    public void getAll() {
        List<Meal> allUser = service.getAll(USER_ID);
        assertMatch(allUser, MEAL_USER_100004, MEAL_USER_100003, MEAL_USER_100002);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_USER_100002);
        updated.setDescription("lunch");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        assertMatch(service.get(100002, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception{
        Meal updated = new Meal(MEAL_USER_100002);
        updated.setDescription("lunch");
        updated.setCalories(330);
        service.update(updated, USER_ID);
        assertMatch(service.get(1, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateWithWrongUser() throws Exception{
        Meal updated = new Meal(MEAL_USER_100002);
        updated.setId(1);
        updated.setDescription("lunch");
        updated.setCalories(330);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(100002, ADMIN_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2019, Month.OCTOBER, 19, 23, 0), "evening dinner", 1555);
        Meal createdUser = service.create(newMeal, ADMIN_ID);
        newMeal.setId(createdUser.getId());
        assertMatch(service.getAll(ADMIN_ID), MEAL_ADMIN_100007, MEAL_ADMIN_100006, MEAL_ADMIN_100005, newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreate() throws Exception {
        service.create(new Meal(LocalDateTime.of(2019, Month.OCTOBER, 19, 19, 0), "Ужин", 1000), USER_ID);
    }
}