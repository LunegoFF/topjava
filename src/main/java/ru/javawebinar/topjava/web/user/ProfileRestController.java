package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.SecurityUtil;

@Controller
public class ProfileRestController extends AbstractUserController {

    SecurityUtil securityUtil = new SecurityUtil();
    public User get() {
        return super.get(securityUtil.getAuthUserId());
    }

    public void delete() {
        super.delete(securityUtil.getAuthUserId());
    }

    public void update(User user) {
        super.update(user, securityUtil.getAuthUserId());
    }
}