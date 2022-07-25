package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.store.Store;

import java.util.List;

public class FindByNameAction implements UserAction {
    @Override
    public String name() {
        return "=== Find items by name ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        String name = input.askStr("Enter name: ");
        List<Item> findByName = tracker.findByName(name);
        if (findByName.size() != 0) {
            for (Item item : findByName) {
                System.out.println("Find value: " + item.getName()
                        + " " + item.getId());
            }
        } else {
            System.out.println("Value not found");
        }
        return true;
    }
}
