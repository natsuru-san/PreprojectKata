package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private final String URL = "jdbc:mysql://localhost/vault?serverTimezone=Europe/Moscow&useSSL=false";
    private final String USER_NAME = "natsuru";
    private final String PASS_KEY = "1234";

    public void execCmd(String cmd) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASS_KEY);
        connection.createStatement().execute(cmd);
        connection.close();
    }
    public List<User> getUsers(String cmd) throws SQLException {
        List<User> list = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USER_NAME, PASS_KEY);
        ResultSet resultSet = connection.createStatement().executeQuery(cmd);
        while (resultSet.next()) {
            User user = new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age"));
            user.setId(resultSet.getLong("id"));
            list.add(user);
        }
        connection.close();
        return list;
    }
}
