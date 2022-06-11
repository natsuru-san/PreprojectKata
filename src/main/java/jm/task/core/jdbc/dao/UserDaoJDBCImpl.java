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
        applyCommandOnDatabase("CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age SMALLINT);");
    }

    public void dropUsersTable() {
        applyCommandOnDatabase("DROP TABLE IF EXISTS users;");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = new Util().getConnection()) {
            String cmd = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(cmd);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = new Util().getConnection()) {
            String cmd = "DELETE FROM users WHERE id=?;";
            PreparedStatement statement = connection.prepareStatement(cmd);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = new Util().getConnection()) {
            List<User> list = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users;");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (GET), дивись информацию тут: " + e);
        }
        return new ArrayList<>();
    }

    public void cleanUsersTable() {
        applyCommandOnDatabase("DELETE FROM users;");
    }
    private void applyCommandOnDatabase(String cmd) {
        try (Connection connection = new Util().getConnection()) {
            connection.createStatement().execute(cmd);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД, дивись информацию дале: " + e);
        }
    }
}
