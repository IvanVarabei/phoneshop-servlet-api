package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.UserDao;
import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.ItemNotFoundException;

public class ArrayListUserDao extends ArrayListGenericDao<User> implements UserDao {
    private ArrayListUserDao() {
    }

    private static class ArrayListUserDaoHolder {
        public static final ArrayListUserDao ARRAY_LIST_USER_DAO_INSTANCE = new ArrayListUserDao();

        private ArrayListUserDaoHolder() {
        }
    }

    public static ArrayListUserDao getInstance() {
        return ArrayListUserDaoHolder.ARRAY_LIST_USER_DAO_INSTANCE;
    }

    @Override
    public synchronized User findByLogin(String login) throws ItemNotFoundException {
        return items.stream().filter(u -> u.getLogin().equals(login)).findAny().orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public synchronized boolean ifExist(String login, String password) {
        return items.stream().anyMatch(u -> u.getLogin().equals(login) && u.getPassword().equals(password));
    }

    @Override
    public synchronized boolean ifLoginExist(String login) {
        return items.stream().anyMatch(u -> u.getLogin().equals(login));
    }
}
