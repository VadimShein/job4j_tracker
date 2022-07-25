package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.Observe;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    @Override
    public void init() {
    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean rsl = false;
        Session session = sf.openSession();
            Item dbItem = findById(id);
        if (dbItem != null) {
            session.beginTransaction();
            item.setId(Integer.parseInt(id));
            session.update(item);
            session.getTransaction().commit();
            session.close();
            rsl = true;
        }
        return rsl;
    }

    @Override
    public boolean delete(String id) {
        int rsl = -1;
        Session session = sf.openSession();
        Item dbItem = findById(id);
        if (dbItem != null) {
            session.beginTransaction();
            session.delete(dbItem);
            session.getTransaction().commit();
            session.close();
            rsl = Integer.parseInt(id);
        }
        return rsl != -1;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item", Item.class).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void findAllByReact(Observe<String> observe) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item", Item.class).list();
        if (result.isEmpty()) {
            observe.receive("Value not found");
        }
        for (Item item : result) {
            observe.receive("Find value: " + item.getId() + " " + item.getName());
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from Item where name = :key", Item.class)
                .setParameter("key", key).getResultList();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findById(String id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, Integer.parseInt(id));
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
