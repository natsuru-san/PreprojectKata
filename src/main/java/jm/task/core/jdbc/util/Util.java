package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {

    public Connection getConnection() throws SQLException {
        String URL = "jdbc:mysql://localhost/vault?serverTimezone=Europe/Moscow&useSSL=false";
        String USER_NAME = "natsuru";
        String PASS_KEY = "1234";
        return DriverManager.getConnection(URL, USER_NAME, PASS_KEY);
    }
}
