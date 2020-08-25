package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.ItemNotFoundException;
import com.es.phoneshop.model.exception.UserAlreadyExistException;

import java.util.ArrayList;

public class ArrayListUserDao {
    private ArrayList<User> items = new ArrayList<>();

    private ArrayListUserDao() {
    }

    private static class SingletonHolder {
        public static final ArrayListUserDao HOLDER_INSTANCE = new ArrayListUserDao();

        private SingletonHolder() {
        }
    }

    public static ArrayListUserDao getInstance() {
        return ArrayListUserDao.SingletonHolder.HOLDER_INSTANCE;
    }

    public synchronized void save(User user) throws UserAlreadyExistException {
        if (items.stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {
            throw new UserAlreadyExistException();
        } else {
            items.add(user);
        }
    }

    public synchronized User find(String login) throws ItemNotFoundException {
        return items.stream().filter(u -> u.getLogin().equals(login)).findAny().orElseThrow(ItemNotFoundException::new);
    }

    public synchronized boolean ifExist(String login, String password)  {
        return items.stream().anyMatch(u -> u.getLogin().equals(login) && u.getPassword().equals(password));
    }
}
