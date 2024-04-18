package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }
    private final String sqlCreateUserTable = "CREATE TABLE IF NOT EXISTS `mydbtest`.`user` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NOT NULL,\n" +
            "  `lastName` VARCHAR(45) NOT NULL,\n" +
            "  `age` INT(3) NOT NULL,\n" +
            "  PRIMARY KEY (`id`))\n" +
            "ENGINE = InnoDB\n" +
            "DEFAULT CHARACTER SET = utf8;\n";
    private final String sqlDeleteUserTable = "drop table if exists user;";
    private final String sqlSaveUser = "insert into user (name, lastName, age) values(?, ?, ?);";
    private final String sqlRemoveUserById = "DELETE FROM user WHERE id = ?;";
    private final String sqlCleanUserTable = "TRUNCATE TABLE user;";

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createSQLQuery(sqlCreateUserTable).executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createSQLQuery(sqlDeleteUserTable).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Logger LOGGER = Logger.getLogger("USER");
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            NativeQuery query = session.createNativeQuery(sqlSaveUser);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3, age);
            query.executeUpdate();
            session.getTransaction().commit();
            LOGGER.log(Level.INFO, String.format("User с именем - %s добавлен в базу данных",name));
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            NativeQuery query = session.createNativeQuery(sqlRemoveUserById);
            query.setParameter(1, id);
            query.executeUpdate();
            session.getTransaction().commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()){
            session.beginTransaction();
            session.createSQLQuery(sqlCleanUserTable).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
