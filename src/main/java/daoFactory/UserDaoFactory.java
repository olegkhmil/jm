package daoFactory;

import dao.UserDAO;
import dao.UserHibernateDAO;
import dao.UserJdbcDAO;

public class UserDaoFactory {
    public UserDAO getUserDAO(String daoType){
        UserDAO dao;
        switch (daoType){
            case "hibernate":
                dao = UserHibernateDAO.getInstance();
                break;
            case "jdbc":
                dao = UserJdbcDAO.getInstance();
                break;
            default:
                throw new IllegalArgumentException("Wrong property:" + daoType);
        }
        return dao;
    }
}
