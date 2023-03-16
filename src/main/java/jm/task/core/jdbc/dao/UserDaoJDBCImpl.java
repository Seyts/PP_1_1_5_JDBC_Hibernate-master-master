package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS mydbtest.users (
                      id int NOT NULL AUTO_INCREMENT,
                      name varchar(15),
                      last_name varchar(25),
                      age tinyint,
                      PRIMARY KEY (id)
                    );""")
                    .execute();
            System.out.println("Таблица Users была создана или уже существовала");
        } catch (SQLException e) {
            System.err.println("An SQLException was thrown: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement("DROP TABLE IF EXISTS mydbtest.users;").execute();
            System.out.println("Таблица Users была удалена или её не существовало");
        } catch (SQLException e) {
            System.err.println("An SQLException was thrown: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(String.format
                            ("INSERT INTO mydbtest.users (name, last_name, age) VALUES('%s','%s',%d);",
                                    name, lastName, age))
                    .execute();
            System.out.printf("User %s %s добавлен в базу данных\n", name, lastName);
        } catch (SQLException e) {
            System.err.println("An SQLException was thrown: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement(String.format
                            ("DELETE FROM mydbtest.users WHERE id = %d;", id))
                    .execute();
            System.out.printf("User c id %d был удалён из таблицы\n", id);
        } catch (SQLException e) {
            System.err.println("An SQLException was thrown: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             ResultSet rs = connection.prepareStatement("SELECT * FROM mydbtest.users").executeQuery()) {
            while(rs.next()) {
                User user;
                userList.add(user = new User(rs.getString("name"), rs.getString("last_name"),
                        rs.getByte("age")));
                user.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection()) {
            connection.prepareStatement("TRUNCATE mydbtest.users").execute();
            System.out.println("Таблица Users была очищена");
        } catch (SQLException e) {
            System.err.println("An SQLException was thrown: " + e.getMessage());
        }
    }
}
