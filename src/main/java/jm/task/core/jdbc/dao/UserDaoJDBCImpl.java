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
        exec("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age SMALLINT);");
    }

    public void dropUsersTable() {
        exec("DROP TABLE IF EXISTS users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        exec("INSERT INTO users (name, lastName, age) VALUES ('" + name + "', '" + lastName + "', '" + age + "');");
    }

    public void removeUserById(long id) {
        exec("DELETE FROM users WHERE id='" + id + "';");
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
        exec("DELETE FROM users;");
    }
    private void exec(String cmd) {
        try {
            new Util().execCmd(cmd);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД, дивись информацию дале: " + e);
        }
    }
}
