package com.es.phoneshop.controller.listener;

import com.es.phoneshop.model.dao.impl.ArrayListUserDao;
import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.UserAlreadyExistException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

public class InitialUsersListener implements ServletContextListener {
    private ArrayListUserDao dao = ArrayListUserDao.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        for(User user : List.of(new User("q", "q", User.Role.ADMIN))){
            try {
                dao.save(user);
            } catch (UserAlreadyExistException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
