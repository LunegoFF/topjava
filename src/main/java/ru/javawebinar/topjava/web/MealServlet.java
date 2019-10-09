package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryRAMImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;
    private MealRepository mealRepository;
    private Map<Integer, MealTo> mealMap;

    @Override
    public void init(){
        mealRepository = new MealRepositoryRAMImpl();
        mealMap = new TreeMap<>();

    }

    private Map<Integer, MealTo> showMeals(){
        mealMap.clear();
        List<MealTo> mealList = MealsUtil.getFiltered(new ArrayList<>(mealRepository.getAll().values()), LocalTime.MIN, LocalTime.MAX, DEFAULT_CALORIES_PER_DAY);
        int i = 0;
        for (Map.Entry<Integer, Meal> entry: mealRepository.getAll().entrySet()) {
            mealMap.put(entry.getKey(), mealList.get(i));
            i++;

        }
        return mealMap;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in get map size = " + mealMap.size());
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("meals", showMeals());
        if (request.getParameter("id") != null){
            int id = Integer.parseInt(request.getParameter("id"));
            log.debug("in get delete id=" + id);
            mealRepository.delete(id);
            request.setAttribute("meals", showMeals());
            response.sendRedirect("meals");
        }else
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in post");
        request.setCharacterEncoding("UTF-8");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(date, description, calories);
        log.debug(meal.toString());
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (mealRepository.getById(id) != null)
                mealRepository.update(id, meal);
        }else
            mealRepository.create(meal);
        request.setAttribute("meals", showMeals());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
