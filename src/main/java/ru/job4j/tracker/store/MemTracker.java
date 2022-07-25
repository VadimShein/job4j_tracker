package ru.job4j.tracker.store;

import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.Observe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MemTracker implements Store, AutoCloseable {

    private final List<Item> items = new ArrayList<>();

    @Override
    public void init() {
    }

    public Item add(Item item) {
        item.setId((int) generateId());
        items.add(item);
        return item;
    }

    public List<Item> findAll() {
        return items;
    }

    public void findAllByReact(Observe<String> observe) {
        if (items.isEmpty()) {
            observe.receive("Value not found");
        }
        for (Item item : items) {
            observe.receive("Find value: " + item.getId() + " " + item.getName());
        }
    }

    public List<Item> findByName(String key) {
        List<Item> foundItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    public Item findById(String id) {
        int index = indexOf(id);
        return index != -1 ? items.get(index) : null;
    }

    private int indexOf(String id) {
        int rsl = -1;
        for (int index = 0; index < items.size(); index++) {
            if (items.get(index).getId().toString().equals(id)) {
                rsl = index;
                break;
            }
        }
        return rsl;
    }

    public boolean replace(String id, Item item) {
        int rsl = -1;
        int index = indexOf(id);
        if (index != -1) {
            items.set(index, item);
            item.setId(Integer.parseInt(id));
            rsl = index;
        }
        return rsl != -1;
    }

    public boolean delete(String id) {
        int rsl = -1;
        int index = indexOf(id);
        if (index != -1) {
            items.remove(index);
            rsl = index;
        }
        return rsl != -1;
    }

    private long generateId() {
        Random rm = new Random();
        return rm.nextLong() + System.currentTimeMillis();
    }

    @Override
    public void close() {
    }
}