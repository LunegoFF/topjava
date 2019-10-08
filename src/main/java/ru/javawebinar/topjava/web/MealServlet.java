package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MealRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealRepository mealRepository = new MealRepositoryImpl();

    public MealServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in get");
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("meals", mealRepository.getAllMeals());
        String action = request.getParameter("action");

        if (action != null && action.equalsIgnoreCase("delete")){

            int mealId = Integer.parseInt(request.getParameter("mealId"));
            log.debug("in get action=delete id=" + mealId);
            mealRepository.deleteMeal(mealId);
            request.setAttribute("meals", mealRepository.getAllMeals());
            request.getRequestDispatcher("meals.jsp").forward(request, response);

        } else if (action != null && action.equalsIgnoreCase("update")){
            log.debug("in get action=update");
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal meal = mealRepository.getMealById(mealId);
            meal.setDateTime(date);
            meal.setDescription(description);
            meal.setCalories(calories);
            log.debug("in get action=update meal=" + meal.toString());
            mealRepository.update(meal);
            request.setAttribute("meals", mealRepository.getAllMeals());
            request.getRequestDispatcher("meals.jsp").forward(request, response);

        } else{
            log.debug("in get in else");
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("in post");
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(id, date, description, calories);
        log.debug(meal.toString());
        mealRepository.addMeal(meal);
        request.setAttribute("meals", mealRepository.getAllMeals());
        request.getRequestDispatcher("meals.jsp").forward(request, response);

    }
}
