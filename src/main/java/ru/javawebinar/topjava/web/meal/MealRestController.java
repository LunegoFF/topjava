package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    private LocalDate startDate = LocalDate.MIN;
    private LocalDate endDate = LocalDate.MAX;
    private LocalTime startTime = LocalTime.MIN;
    private LocalTime endTime = LocalTime.MAX;

    @Autowired
    private MealService service;

    public Collection<MealTo> getAll(int userId) {
        log.info("getAll of userId {} ", userId);
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getAllWithFilter(int userId, String startDate, String endDate, String startTime, String endTime){
        log.info("getAll with filter of userId {} ", userId);
        if (!startDate.equals(""))
            this.startDate = LocalDate.parse(startDate);
        if (!endDate.equals(""))
            this.endDate = LocalDate.parse(endDate);
        if (!startTime.equals(""))
            this.startTime = LocalTime.parse(startTime);
        if (!endTime.equals(""))
            this.endTime = LocalTime.parse(endTime);
        Collection<MealTo> meals = MealsUtil.getTos(service.getAllWithFilter(userId, this.startDate, this.endDate, this.startTime, this.endTime), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        log.info("filter parametrs {}, {}, {}, {}", this.startDate, this.endDate, this.startTime, this.endTime);
        this.startDate = LocalDate.MIN;
        this.endDate = LocalDate.MAX;
        this.startTime = LocalTime.MIN;
        this.endTime = LocalTime.MAX;
        return meals;
    }

    public Meal get(int id, int userId) {
        log.info("get {} of user {} ", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete {} of user {} ", id, userId);
        service.delete(id, userId);
    }

    public void save(Meal meal, int id) {

        if (id == 0) {
            log.info("create {} ", meal);
            service.create(meal, meal.getUserId());
        }else {
            log.info("update {} with id={} ", meal, id);
            service.update(meal, id);
        }
    }
}