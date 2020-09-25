package com.es.phoneshop.model.service;

import com.es.phoneshop.model.entity.User;
import com.es.phoneshop.model.exception.ItemNotFoundException;

public interface UserService {
    void save(User item);

    void delete(long id);

    User findByLogin(String login) throws ItemNotFoundException;

    boolean ifExist(String login, String password);

    boolean ifLoginExist(String login);
}
