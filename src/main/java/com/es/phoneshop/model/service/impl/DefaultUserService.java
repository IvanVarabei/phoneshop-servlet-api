package com.es.phoneshop.model.service.impl;

import com.es.phoneshop.model.dao.UserDao;
import com.es.phoneshop.model.dao.impl.ArrayListUserDao;
import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.service.UserService;

public class DefaultUserService implements UserService {
    private UserDao userDao = ArrayListUserDao.getInstance();

    private DefaultUserService() {
    }

    private static class DefaultUserServiceHolder {
        private static final DefaultUserService DEFAULT_USER_SERVICE_INSTANCE =
                new DefaultUserService();
    }

    public static DefaultUserService getInstance() {
        return DefaultUserServiceHolder.DEFAULT_USER_SERVICE_INSTANCE;
    }

    @Override
    public User findByLogin(String login) throws ItemNotFoundException {
        return userDao.findByLogin(login);
    }

    @Override
    public boolean ifExist(String login, String password) {
        return userDao.ifExist(login, password);
    }
}
