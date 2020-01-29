package service;

import dao.UserDAO;
import exception.DBException;
import model.User;
import dao.UserJdbcDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserServiceJDBC implements UserService {
    private static UserServiceJDBC userServiceJDBC;

    private UserDAO userDAO = getUserDAO();


    private UserServiceJDBC() {
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


    private static Connection getMysqlConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:mysql://").                                        //db type
                    append("localhost:").                                           //host name
                    append("3306/").                                                //port
                    append("db_pp1?").                                              //db name
                    append("useUnicode=true&serverTimezone=UTC&useSSL=false")/*.    //timezone
                    append("user=root&").                                           //login
                    append("password=root")*/;                                      //password
            System.out.println("URL: " + url + "\n");
            Connection connection = DriverManager.getConnection(url.toString(), "root", "root");
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private static UserJdbcDAO getUserDAO() {
        return new UserJdbcDAO(getMysqlConnection());
    }
}
