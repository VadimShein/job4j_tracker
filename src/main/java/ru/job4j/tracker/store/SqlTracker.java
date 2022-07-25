package ru.job4j.tracker.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.tracker.entity.Item;
import ru.job4j.tracker.Observe;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SqlTracker implements Store, AutoCloseable {
    private Connection conn;
    private static final Logger LOG = LoggerFactory.getLogger(SqlTracker.class);

    public SqlTracker() {
    }

    public SqlTracker(Connection connection) {
        this.conn = connection;
    }

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            conn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws Exception {
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement st = this.conn.prepareStatement("insert into items(name) values(?)",
                Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                System.out.println(String.format("%s %s", rs.getInt("id"), rs.getString("name")));
                item.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        int rsl = 0;
        try (PreparedStatement st = this.conn.prepareStatement("update items set name=? where id=?")) {
            st.setString(1, item.getName());
            st.setInt(2, Integer.parseInt(id));
            rsl = st.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl != 0;
    }

    @Override
    public boolean delete(String id) {
        int rsl = 0;
        try (PreparedStatement st = this.conn.prepareStatement("delete from items where id=?")) {
            st.setInt(1, Integer.parseInt(id));
            rsl = st.executeUpdate();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return rsl != 0;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement st = this.conn.prepareStatement("select * from items")) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item newItem = new Item(rs.getString("name"));
                newItem.setId(rs.getInt("id"));
                items.add(newItem);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public void findAllByReact(Observe<String> observe) {
        try (PreparedStatement st = this.conn.prepareStatement("select * from items")) {
            ResultSet rs = st.executeQuery();
            if (!rs.isBeforeFirst()) {
                observe.receive("Value not found");
            }
            while (rs.next()) {
                observe.receive("Find value: " + rs.getInt("id") + " "
                        + rs.getString("name"));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement st = this.conn.prepareStatement("select * from items where name=?")) {
            st.setString(1, key);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item newItem = new Item(rs.getString("name"));
                newItem.setId(rs.getInt("id"));
                items.add(newItem);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return items;
    }

    @Override
    public Item findById(String id) {
        Item newItem = null;
        try (PreparedStatement st = this.conn.prepareStatement("select * from items where id=?")) {
            st.setInt(1, Integer.parseInt(id));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                newItem = new Item(rs.getString("name"));
                newItem.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return newItem;
    }
}