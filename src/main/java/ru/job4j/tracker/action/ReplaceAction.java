package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.store.Store;

public class ReplaceAction implements UserAction {
    @Override
    public String name() {
        return "=== Edit item ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        String id = input.askStr("Enter id: ");
        Item newItem = new Item(input.askStr("Enter name: "));
        if (tracker.replace(id, newItem)) {
            System.out.println("performed");
        } else {
            System.out.println("not performed");
        }
        return true;
    }
}
