package daoFactory;

import dao.UserDAO;
import dao.UserHibernateDAO;
import dao.UserJdbcDAO;
import util.PropertyReader;

import java.util.Properties;

public class UserDaoFactory {
    public UserDAO getUserDAO() {
        Properties properties = PropertyReader.getProperties("db.properties");
        UserDAO dao;
        switch (properties.getProperty("daoType")) {
            case "hibernate":
                dao = UserHibernateDAO.getInstance();
                break;
            case "jdbc":
                dao = UserJdbcDAO.getInstance();
                break;
            default:
                throw new IllegalArgumentException("Wrong property");
        }
        return dao;
    }
}
