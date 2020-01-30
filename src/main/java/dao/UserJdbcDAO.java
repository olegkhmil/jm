package dao;

import exception.DBException;
import model.User;
import util.DBHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserJdbcDAO implements UserDAO {
    private static UserJdbcDAO userJdbcDAO;
    private Connection connection;

    private UserJdbcDAO() {
        connection = DBHelper.getInstance().getMysqlConnection();
    }
    public static UserJdbcDAO getInstance() {
        if (userJdbcDAO == null) {
            userJdbcDAO = new UserJdbcDAO();
        }
        return userJdbcDAO;
    }

    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM USERS")) {
            while (result.next()) {
                users.add(new User(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getInt("age"),
                        result.getString("email"),
                        result.getString("password")));
            }
        } catch (SQLException e) {
            users = null;
        }
        return users;
    }

    public User getUserByEmail(String email) throws DBException {
        try (Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery("select * from users where email='" + email + "'")) {
            if (result.next()) {
                Long id = result.getLong("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                String mail = result.getString("email");
                String password = result.getString("password");
                return new User(id, name, age, mail, password);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return null;
    }

    public User getUserById(Long userId) throws DBException {
        try (Statement stmt = connection.createStatement();
             ResultSet result = stmt.executeQuery("select * from users where id='" + userId + "'")) {
            if (result.next()) {
                Long id = result.getLong("id");
                String name = result.getString("name");
                int age = result.getInt("age");
                String mail = result.getString("email");
                String password = result.getString("password");
                return new User(id, name, age, mail, password);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return null;
    }

    public Long createUser(String name, int age, String email, String password) throws DBException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, age, email, password) VALUES (?,?,?,?)")) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, email);
            statement.setString(4, password);
            statement.executeUpdate();
            return 1L;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void deleteUser(Long id) throws DBException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users WHERE id='" + id + "'");
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void updateUser(Long id, String name, int age, String email, String password) throws DBException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE users SET name = ?,age=?,email=?,password=? WHERE id = ?")) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                throw new DBException(e1);
            }
            throw new DBException(e);
        }
    }
}
