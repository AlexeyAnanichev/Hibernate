package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;

public class UserDaoJDBCImpl implements UserDao {
    private final Util util = new Util();
    private int id;

    public UserDaoJDBCImpl() {

    }

    public int getId() throws SQLException {
        util.connectToDB();
        Statement statement = util.getConnection().createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Users");
        while (rs.next()) {
            id = rs.getInt("id");
        }
        util.getConnection().close();
        return id;
    }

    public void createUsersTable() throws SQLException {
        try {
            util.connectToDB();
            util.getConnection().prepareStatement("CREATE TABLE Users" + "(id INTEGER not NULL," + "name VARCHAR(50),"
                    + "lastName VARCHAR(50)," + "age INTEGER," + "PRIMARY KEY (id))").execute();
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Таблица уже создана");
        } finally {
            util.getConnection().close();
        }
    }

    public void dropUsersTable() throws SQLException {
        try {
            util.connectToDB();
            util.getConnection().prepareStatement("DROP TABLE Users").execute();
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Удаление не требуется. Таблицы не существует");
        } finally {
            util.getConnection().close();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try {
            util.connectToDB();
            PreparedStatement ps = util.getConnection()
                    .prepareStatement("INSERT INTO Users VALUES(?,?,?,?)");
            ps.setInt(1, getId() + 1);
            ps.setString(2, name);
            ps.setString(3, lastName);
            ps.setByte(4, age);
            ps.execute();
            System.out.printf("User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Удаление не требуется. Таблицы не существует");
        } finally {
            util.getConnection().close();
        }
    }

    public void removeUserById(long id) throws SQLException {
        try {
            util.connectToDB();
            PreparedStatement ps = util.getConnection()
                    .prepareStatement("DELETE FROM Users WHERE id=?");
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Удаление не требуется. Таблицы не существует");
        } finally {
            util.getConnection().close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        try {
            util.connectToDB();
            Statement statement = util.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                int age = rs.getInt("age");
                User user = new User(name, lastName, (byte) age);
                list.add(user);
            }
            util.getConnection().close();
            return list;
        } catch (SQLSyntaxErrorException e) {
            System.err.println("Удаление не требуется. Таблицы не существует");
        } finally {
            util.getConnection().close();
        }
        return list;
    }

    public void cleanUsersTable() throws SQLException {
        util.connectToDB();
        util.getConnection().prepareStatement("DELETE FROM Users").execute();
        util.getConnection().close();
    }
}
