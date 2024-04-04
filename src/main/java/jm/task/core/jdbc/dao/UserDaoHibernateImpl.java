package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static jm.task.core.jdbc.util.HibernateUtil.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    private static Session session = getSessionFactory().openSession();


    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = session.beginTransaction();
        String sql = String.format("insert into user (name, lastName, age) values('%s', '%s', %d);",name, lastName, age);
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(String.format("DELETE FROM user WHERE id = %d;",id)).executeUpdate();
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        return session.createSQLQuery("SELECT * FROM user").addEntity(User.class).getResultList();
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = session.beginTransaction();
        session.createSQLQuery("TRUNCATE TABLE user;").executeUpdate();
        transaction.commit();
    }
}
