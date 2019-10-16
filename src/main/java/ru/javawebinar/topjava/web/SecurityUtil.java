package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);
    private static int authUserId;

    public SecurityUtil() {
    }

    public int getAuthUserId() {
        log.info("return userId {}", authUserId);
        return authUserId;
    }

    public void setAuthUserId(int id){

        log.info("set userId {}", id);
        authUserId = id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}