package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;
    private MealRestController controller;
    private SecurityUtil securityUtil = new SecurityUtil();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        log.info("in post");
        switch (action == null ? "all" : action) {
            case "filter":
                log.info("in post with filter");
                request.setAttribute("meals",
                    controller.getAllWithFilter(securityUtil.getAuthUserId(),
                            request.getParameter("startDate").isEmpty() ? "" : request.getParameter("startDate"),
                            request.getParameter("endDate").isEmpty() ? "" : request.getParameter("endDate"),
                            request.getParameter("startTime").isEmpty() ? "" : request.getParameter("startTime"),
                            request.getParameter("endTime").isEmpty() ? "" : request.getParameter("endTime")));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
                Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                        securityUtil.getAuthUserId(),
                        LocalDateTime.parse(request.getParameter("dateTime")),
                        request.getParameter("description"),
                        Integer.parseInt(request.getParameter("calories")));
                log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
                controller.save(meal, id.isEmpty() ? 0 : Integer.valueOf(id));
                response.sendRedirect("meals");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");


        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id, securityUtil.getAuthUserId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(securityUtil.getAuthUserId(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request), securityUtil.getAuthUserId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        controller.getAll(securityUtil.getAuthUserId()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    @Override
    public void destroy(){
        super.destroy();
        appCtx.close();
    }
}
