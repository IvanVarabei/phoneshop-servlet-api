package com.es.phoneshop.controller.listener;

import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.service.UserService;
import com.es.phoneshop.model.service.impl.DefaultUserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class InitialUsersListener implements ServletContextListener {
    private UserService userService = DefaultUserService.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        for (User user : List.of(new User("qqq", "qqq", User.Role.ADMIN))) {
            userService.save(user);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
