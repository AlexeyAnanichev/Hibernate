package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Freddie", "Mercury", (byte) 45);
        service.saveUser("Corey", "Taylor", (byte) 47);
        service.saveUser("Tim", "Bergling", (byte) 28);
        service.saveUser("Alecia", "Moore", (byte) 41);
        System.out.println(service.getAllUsers().toString());
        service.cleanUsersTable();
        service.dropUsersTable();
        //service.removeUserById(2);
    }
}
