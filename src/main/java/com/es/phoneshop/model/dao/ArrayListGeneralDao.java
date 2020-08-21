package com.es.phoneshop.model.dao;

import com.es.phoneshop.model.entity.StorageItem;

import java.util.ArrayList;
import java.util.List;

public class ArrayListGeneralDao<T extends StorageItem>{
    protected List<T> items = new ArrayList<>();
    protected long id;

    public synchronized void save(T storageItem) {
        if (storageItem.getId() != null && items.removeIf(p -> storageItem.getId().equals(p.getId()))) {
            items.add(storageItem);
            return;
        }
        storageItem.setId(++id);
        items.add(storageItem);
    }
}
