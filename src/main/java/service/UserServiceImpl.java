package service;

import dao.UserDAO;
import daoFactory.UserDaoFactory;
import exception.DBException;
import model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static UserServiceImpl userServiceImpl;
    private UserDAO userDAO;

    private UserServiceImpl() {
        UserDaoFactory userDaoFactory = new UserDaoFactory();
        userDAO = userDaoFactory.getUserDAO();
    }

    public static UserServiceImpl getInstance() {
        if (userServiceImpl == null) {
            userServiceImpl = new UserServiceImpl();
        }
        return userServiceImpl;
    }


    @Override
    public List<User> getAllUsers() throws DBException {
        return userDAO.readAllUsers();
    }

    @Override
    public User getUserById(Long id) throws DBException {
        return userDAO.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email) throws DBException {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public boolean addUser(String name, int age, String email, String password, String role) throws DBException {
        if (userDAO.getUserByEmail(email) == null) {
            return userDAO.createUser(name, age, email, password, role) > 0;
        } else return false;
    }

    @Override
    public boolean deleteUser(Long id) throws DBException {
        if (userDAO.getUserById(id) != null) {
            userDAO.deleteUser(id);
            return true;
        } else return false;
    }

    @Override
    public boolean updateUser(Long id, String name, int age, String email, String password, String role) throws DBException {
        if (userDAO.getUserById(id) != null
                && (userDAO.getUserByEmail(email) == null ||
                userDAO.getUserByEmail(email).getId().equals(id))) {
            userDAO.updateUser(id, name, age, email, password, role);
            return true;
        } else return false;
    }

}
