package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.store.Store;

public class FindByIdAction implements UserAction {
    @Override
    public String name() {
        return "=== Find item by Id ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        String id = input.askStr("Enter id: ");
        Item findById =  tracker.findById(id);
        if (findById != null) {
            System.out.println("Find value: " + findById.getName() + " " + findById.getId());
        } else {
            System.out.println("Value not found");
        }
        return true;
    }
}
