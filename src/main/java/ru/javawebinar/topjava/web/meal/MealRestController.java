package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<MealTo> getAll(int userId) {
        log.info("getAll of userId {} ", userId);
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getAllWithFilter(int userId, String startDate, String endDate, String startTime, String endTime){
        log.info("getAll with filter of userId {} ", userId);

        return MealsUtil.getTos(service.getAllWithFilter(userId, startDate, endDate, startTime, endTime), MealsUtil.DEFAULT_CALORIES_PER_DAY);
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