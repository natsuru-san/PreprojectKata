package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sf = Util.getSf();
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age SMALLINT);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";
    private static final String CLEAN_TABLE = "DELETE FROM users;";
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        exec(CREATE_TABLE);
    }

    @Override
    public void dropUsersTable() {
        exec(DROP_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction t = null;
        try (Session s = sf.openSession()) {
            t = s.beginTransaction();
            s.save(new User(name, lastName, age));
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction t = null;
        try (Session s = sf.openSession()) {
            t = s.beginTransaction();
            s.delete(s.get(User.class, id));
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction t = null;
        List<User> users = null;
        try (Session s = sf.openSession()) {
            CriteriaQuery<User> c = s.getCriteriaBuilder().createQuery(User.class);
            c.from(User.class);
            t = s.beginTransaction();
            users = s.createQuery(c).getResultList();
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        exec(CLEAN_TABLE);
    }

    private void exec(String cmd) {
        Transaction t = null;
        try (Session s = sf.openSession()) {
            t = s.beginTransaction();
            s.createNativeQuery(cmd).executeUpdate();
            t.commit();
        } catch (HibernateException e) {
            if (t != null) {
                t.rollback();
            }
            e.printStackTrace();
        }
    }
}
