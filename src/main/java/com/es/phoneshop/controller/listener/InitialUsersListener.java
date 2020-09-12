package com.es.phoneshop.controller.listener;

import com.es.phoneshop.model.dao.impl.ArrayListUserDao;
import com.es.phoneshop.model.entity.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class InitialUsersListener implements ServletContextListener {
    private ArrayListUserDao dao = ArrayListUserDao.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        for (User user : List.of(new User("q", "q", User.Role.ADMIN))) {
            dao.save(user);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
