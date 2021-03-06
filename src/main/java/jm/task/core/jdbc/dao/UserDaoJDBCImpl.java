package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE_CMD = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age SMALLINT);";
    private static final String DROP_TABLE_CMD = "DROP TABLE IF EXISTS users;";
    private static final String SAVE_USER_INTO_TABLE_CMD = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
    private static final String REMOVE_USER_FROM_TABLE_CMD = "DELETE FROM users WHERE id=?;";
    private static final String LIST_USERS_CMD = "SELECT * FROM users;";
    private static final String DELETE_ALL_USERS_CMD = "DELETE FROM users;";
    private static final Connection connection = new Util().getConnection();
    private final Logger LOG = Logger.getLogger(this.getClass().getName());
    
    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        applyCommandOnDatabase(CREATE_TABLE_CMD);
    }

    public void dropUsersTable() {
        applyCommandOnDatabase(DROP_TABLE_CMD);
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE_USER_INTO_TABLE_CMD);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(REMOVE_USER_FROM_TABLE_CMD);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try {
            List<User> list = new ArrayList<>();
            ResultSet resultSet = connection.createStatement().executeQuery(LIST_USERS_CMD);
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "?????????????? ???????????? ?? ???? (GET), ???????????? ???????????????????? ??????: " + e);
        }
        return new ArrayList<>();
    }

    public void cleanUsersTable() {
        applyCommandOnDatabase(DELETE_ALL_USERS_CMD);
    }
    private void applyCommandOnDatabase(String sqlExpression) {
        try {
            connection.createStatement().execute(sqlExpression);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "?????????????? ???????????? ?? ????, ???????????? ???????????????????? ????????: " + e);
        }
    }
}
