package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.ItemNotFoundException;

public interface UserDao extends GenericDao<User>{
    User findByLogin(String login) throws ItemNotFoundException;

    boolean ifExist(String login, String password);

    boolean ifLoginExist(String login);
}
