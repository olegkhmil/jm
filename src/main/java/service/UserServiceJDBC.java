package service;

import dao.UserDAO;
import daoFactory.UserDaoFactory;
import exception.DBException;
import model.User;
import dao.UserJdbcDAO;
import util.PropertyReader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserServiceJDBC implements UserService {
    private static UserServiceJDBC userServiceJDBC;
    private UserDAO userDAO;

    private UserServiceJDBC() {
        UserDaoFactory userDaoFactory = new UserDaoFactory();
        Properties properties = PropertyReader.getProperties();
        userDAO = userDaoFactory.getUserDAO(properties.getProperty("daoTypeJ"));
    }

    public static UserServiceJDBC getInstance(){
        if(userServiceJDBC == null){
            userServiceJDBC = new UserServiceJDBC();
        }
        return userServiceJDBC;
    }

    public List<User> getAllUsers() throws DBException {
        return userDAO.readAllUsers();  //may return null
    }

    @Override
    public User getUserById(Long id) throws DBException {
        return userDAO.getUserById(id);
    }

    public boolean addUser(String name, int age, String email, String password) throws DBException {
            if (userDAO.getUserByEmail(email) == null) {
                userDAO.createUser(name, age, email, password);
                return true;
            } else return false;
    }

    public boolean deleteUser(Long id) throws DBException {
        if (userDAO.getUserById(id) != null) {
            userDAO.deleteUser(id);
            return true;
        } else return false;
    }

    public boolean updateUser(Long id, String name, int age, String email, String password) throws DBException {
        if (userDAO.getUserById(id) != null && userDAO.getUserByEmail(email) == null) {
            userDAO.updateUser(id, name, age, email, password);
            return true;
        } else return false;
    }


    public static UserServiceJDBC getInstanceJDBC() {
        if (userServiceJDBC == null) {
            userServiceJDBC = new UserServiceJDBC();
        }
        return userServiceJDBC;
    }
}
