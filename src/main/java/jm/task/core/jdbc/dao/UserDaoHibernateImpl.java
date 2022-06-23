package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getSessionFactory();
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age SMALLINT);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users;";
    private static final String CLEAN_TABLE = "DELETE FROM users;";
    public UserDaoHibernateImpl() {
    }
    @Override
    public void createUsersTable() {
        execNative(CREATE_TABLE);
    }

    @Override
    public void dropUsersTable() {
        execNative(DROP_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @SuppressWarnings("unchecked") //Довелося придушити повідомлення IDE оскільки CollectionFilterImpl стирає параметризований тип. З цим не можна що-небудь вдіяти.
    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        execNative(CLEAN_TABLE);
    }

    private void execNative(String cmd) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(cmd).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
