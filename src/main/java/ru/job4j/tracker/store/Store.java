package ru.job4j.tracker.store;

import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.Observe;

import java.util.List;

public interface Store extends AutoCloseable {
    void init();
    Item add(Item item);
    boolean replace(String id, Item item);
    boolean delete(String id);
    List<Item> findAll();
    void findAllByReact(Observe<String> observe);
    List<Item> findByName(String key);
    Item findById(String id);
}