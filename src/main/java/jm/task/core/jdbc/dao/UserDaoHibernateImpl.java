package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.SQLGrammarException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDaoHibernateImpl implements UserDao {

    private final Util util = new Util();
    private final UserDaoJDBCImpl dao = new UserDaoJDBCImpl();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() throws SQLSyntaxErrorException {
        try {
            SessionFactory sessionFactory = util.buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE Users" + "(id INTEGER not NULL AUTO_INCREMENT,"
                    + "name VARCHAR(50)," + "lastName VARCHAR(50)," + "age INTEGER," + "PRIMARY KEY (id))")
                    .executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            e.printStackTrace();
            util.buildSessionFactory().close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            SessionFactory sessionFactory = util.buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE Users").executeUpdate();
            transaction.commit();
            session.close();
        } catch (SQLGrammarException e) {
            System.err.println("Удаление не требуется. Таблицы не существует");
            util.buildSessionFactory().close();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            SessionFactory sessionFactory = util.buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            util.buildSessionFactory().close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            SessionFactory sessionFactory = util.buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            SQLQuery query = session.createSQLQuery("DELETE FROM Users WHERE id = (:id)");
            query.setLong("id", id);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            util.buildSessionFactory().close();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            SessionFactory sessionFactory = util.buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            list = session.createSQLQuery("SELECT * FROM Users").addEntity(User.class).list();
            transaction.commit();
            session.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            util.buildSessionFactory().close();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try {
            SessionFactory sessionFactory = util.buildSessionFactory();
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM Users").executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            util.buildSessionFactory().close();
        }
    }
}
