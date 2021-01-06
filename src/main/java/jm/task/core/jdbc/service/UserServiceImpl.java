package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDaoJDBCImpl dao = new UserDaoJDBCImpl();
    private final UserDaoHibernateImpl daoHibernate = new UserDaoHibernateImpl();

    public void createUsersTable() {
        //dao.createUsersTable();
        try {
            daoHibernate.createUsersTable();
        } catch (SQLSyntaxErrorException throwables) {
            System.err.println("Таблица уже создана");
        }
    }

    public void dropUsersTable() throws SQLException {
        //  dao.dropUsersTable();
        daoHibernate.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        //  dao.saveUser(name, lastName, age);
        daoHibernate.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws SQLException {
        //dao.removeUserById(id);
        daoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() throws SQLException {
        //return dao.getAllUsers();
        return daoHibernate.getAllUsers();
    }

    public void cleanUsersTable() throws SQLException {
        //dao.cleanUsersTable();
        daoHibernate.cleanUsersTable();
    }
}
