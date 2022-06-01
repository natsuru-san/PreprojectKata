package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Logger LOG = Logger.getLogger(this.getClass().getName());
    
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try {
            new Util().execCmd("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age SMALLINT);");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (CREATE), дивись информацию ниже: " + e);
        }
    }

    public void dropUsersTable() {
        try {
            new Util().execCmd("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (DROP), дивись информацию ниже: " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            new Util().execCmd("INSERT INTO users (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', '" + age + "');");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (SAVE), дивись информацию ниже: " + e);
        }
    }

    public void removeUserById(long id) {
        try {
            new Util().execCmd("DELETE FROM users WHERE id='" + id + "';");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (REMOVE_USER), дивись информацию ниже: " + e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return new Util().getUsers("SELECT * FROM users;");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (GET), дивись информацию тут: " + e);
        }
        return new ArrayList<>();
    }

    public void cleanUsersTable() {
        try {
            new Util().execCmd("DELETE FROM users;");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (CLEAN), дивись информацию ниже: " + e);
        }
    }
}
