package service;

import dao.UserHibernateDAO;
import exception.DBException;
import model.User;

import java.util.List;

public class UserServiceHibernate implements UserService {
    private static UserServiceHibernate userServiceHibernate;
    private UserHibernateDAO userHibernateDAO;

    private UserServiceHibernate() {
        userHibernateDAO = UserHibernateDAO.getInstance();
    }

    public static UserServiceHibernate getInstanceUSH() {
        if (userServiceHibernate == null) {
            userServiceHibernate = new UserServiceHibernate();
        }
        return userServiceHibernate;
    }


    @Override
    public List<User> getAllUsers() throws DBException {
        return userHibernateDAO.readAllUsers();
    }

    @Override
    public User getUserById(Long id) throws DBException {
        return userHibernateDAO.getUserById(id);
    }

    @Override
    public boolean addUser(String name, int age, String email, String password) throws DBException {
        if (userHibernateDAO.getUserByEmail(email) == null) {
            return userHibernateDAO.createUser(name, age, email, password) > 0;
        } else return false;
    }

    @Override
    public boolean deleteUser(Long id) throws DBException {
        if (userHibernateDAO.getUserById(id) != null) {
            userHibernateDAO.deleteUser(id);
            return true;
        } else return false;
    }

    @Override
    public boolean updateUser(Long id, String name, int age, String email, String password) throws DBException {
        if (userHibernateDAO.getUserById(id) != null
                && (userHibernateDAO.getUserByEmail(email) == null ||
                userHibernateDAO.getUserByEmail(email).getId().equals(id))) {
            userHibernateDAO.updateUser(id, name, age, email, password);
            return true;
        } else return false;
    }

}
