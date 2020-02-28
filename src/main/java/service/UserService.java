package service;

import exception.DBException;
import model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers() throws DBException;

    User getUserById(Long id) throws DBException;

    User getUserByEmail(String email) throws DBException;

    boolean addUser(String name, int age, String email, String password, String role) throws DBException;

    boolean deleteUser(Long id) throws DBException;

    boolean updateUser(Long id, String name, int age, String email, String password, String role) throws DBException;
}
