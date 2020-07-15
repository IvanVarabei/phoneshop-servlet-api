package com.es.phoneshop.model.dao.storage;

import com.es.phoneshop.model.entity.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PhoneStock {
    private static volatile PhoneStock instance;
    private final List<Product> phoneList = Collections.synchronizedList(new ArrayList<>());

    private PhoneStock() {
    }

    public static PhoneStock getInstance() {
        PhoneStock localInstance = instance;
        if (localInstance == null) {
            synchronized (PhoneStock.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new PhoneStock();
                }
            }
        }
        return localInstance;
    }

    public List<Product> getPhoneList() {
        return phoneList;
    }
}