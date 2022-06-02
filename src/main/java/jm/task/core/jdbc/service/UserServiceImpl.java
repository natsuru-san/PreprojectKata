package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDaoHibernateImpl h = new UserDaoHibernateImpl();
    public void createUsersTable() {
        h.createUsersTable();
    }

    public void dropUsersTable() {
        h.dropUsersTable();
    }
    public void saveUser(String name, String lastName, byte age) {
        h.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        h.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return h.getAllUsers();
    }

    public void cleanUsersTable() {
        h.cleanUsersTable();
    }
}
