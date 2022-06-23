package jm.task.core.jdbc.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private final Logger LOG = Logger.getLogger(this.getClass().getName());
    private static final String URL = "jdbc:mysql://localhost/vault?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USER_NAME = "natsuru";
    private static final String PASS_KEY = "1234";
    public Connection getConnection() { //Не розумію, навіщо обробляти виключення тут, якщо їх все одно потрібно буде обробити в DAO...
        try {
            return DriverManager.getConnection(URL, USER_NAME, PASS_KEY);
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Помилка роботи з БД (GET), дивись информацию тут: " + e);
            return null;
        }
    }
}
