package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.store.Store;

public class CreateAction implements UserAction {
    @Override
public String name() {
    return "=== Create a new Item ====";
}

    @Override
    public boolean execute(Input input, Store tracker) {
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        tracker.add(item);
        System.out.println("Added: " + item.getName() + " " + item.getId());
        return true;
    }
}