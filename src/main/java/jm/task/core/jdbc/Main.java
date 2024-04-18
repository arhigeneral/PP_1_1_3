package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        Logger LOGGER = Logger.getLogger("USER");
        userDao.saveUser("Name1", "LastName1", (byte) 20);
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        userDao.saveUser("Name4", "LastName4", (byte) 38);

        LOGGER.log(Level.INFO, userDao.getAllUsers().toString());
        userDao.removeUserById(1);
        LOGGER.log(Level.INFO, userDao.getAllUsers().toString());
        userDao.cleanUsersTable();
        LOGGER.log(Level.INFO, userDao.getAllUsers().toString());
        userDao.dropUsersTable();

    }
}
