package com.es.phoneshop.model.dao.impl;

import com.es.phoneshop.model.dao.GenericDao;
import com.es.phoneshop.model.entity.StorageItem;
import com.es.phoneshop.model.exception.ItemNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListGenericDao<T extends StorageItem> implements GenericDao<T> {
    protected List<T> items = new ArrayList<>();
    protected long id;

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public synchronized void save(T storageItem) {
        if (storageItem.getId() != null && items.removeIf(i -> storageItem.getId().equals(i.getId()))) {
            items.add(storageItem);
            return;
        }
        storageItem.setId(++id);
        items.add(storageItem);
    }

    @Override
    public synchronized T find(Long id) throws ItemNotFoundException {
        return items.stream().filter(i -> i.getId().equals(id)).findAny()
                .orElseThrow(ItemNotFoundException::new);
    }

    @Override
    public synchronized List<T> findAll() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public synchronized void delete(long id) {
        items.removeIf(i -> i.getId().equals(id));
    }
}
