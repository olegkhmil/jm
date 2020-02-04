package service;

import exception.DBException;
import model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers() throws DBException;

    public User getUserById(Long id) throws DBException;

    boolean addUser(String name, int age, String email, String password) throws DBException;

    public boolean deleteUser(Long id) throws DBException;

    public boolean updateUser(Long id, String name, int age, String email, String password) throws DBException;
}
