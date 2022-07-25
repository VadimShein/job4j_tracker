package ru.job4j.tracker.action;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.store.Store;

public class FindAllAction implements UserAction {
    @Override
    public String name() {
        return "=== Find all items ====";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        tracker.findAllByReact(System.out::println);
        return true;
    }
}
