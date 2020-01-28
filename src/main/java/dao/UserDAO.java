package dao;

import exception.DBException;
import model.User;

import java.util.List;

public interface UserDAO {
    List<User> readAllUsers() throws DBException;

    User getUserByEmail(String email) throws DBException;

    User getUserById(Long userId) throws DBException;

    Long createUser(String name, int age, String email, String password) throws DBException;

    void deleteUser(Long id) throws DBException;

    void updateUser(Long id, String name, int age, String email, String password) throws DBException;

}
