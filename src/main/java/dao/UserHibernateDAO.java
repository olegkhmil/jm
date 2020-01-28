package dao;

import exception.DBException;
import model.User;
import org.hibernate.*;
import util.DBHelper;

import java.util.List;

public class UserHibernateDAO implements UserDAO {
    private static UserHibernateDAO userHibernateDAO;
    private SessionFactory sessionFactory = DBHelper.getSessionFactory();


    public static UserHibernateDAO getInstance() {
        if (userHibernateDAO == null) {
            userHibernateDAO = new UserHibernateDAO();
        }
        return userHibernateDAO;
    }

    private UserHibernateDAO() {
    }

    @Override
    public List<User> readAllUsers() throws DBException {
        Session session = sessionFactory.openSession();
        List<User>users;
        try {
            Query query = session.createQuery("from User");
            users = (List<User>) query.list();
            session.close();
            return users;
        } catch (HibernateException e) {
            throw new DBException(e);
        }

    }

    @Override
    public User getUserByEmail(String email) throws DBException {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            User user = (User) query.uniqueResult();
            session.close();
            return user;
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    @Override
    public User getUserById(Long userId) throws DBException { //may return null
        Session session = sessionFactory.openSession();
        try {
            User user = (User) session.get(User.class, userId);
            session.close();
            return user;
        }catch (HibernateException e){
            throw new DBException(e);
        }
    }

    @Override
    public Long createUser(String name, int age, String email, String password) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Long id;
        try {
            id = (Long) session.save(new User(name, age, email, password));
            transaction.commit();
            session.close();
            return id;
        } catch (HibernateException e) {
            transaction.rollback();
            session.close();
            throw new DBException(e);
        }
    }

    @Override
    public void deleteUser(Long id) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query query = session.createQuery("delete from User where id = :id");
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (HibernateException e){
            transaction.rollback();
            session.close();
            throw new DBException(e);
        }
    }

    @Override
    public void updateUser(Long id, String name, int age, String email, String password) throws DBException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try{
            Query query = session.createQuery(
                    "update User set name=:name, age=:age, email=:email, password=:password where id=:id");
            query.setParameter("name", name);
            query.setParameter("age", age);
            query.setParameter("email", email);
            query.setParameter("password", password);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            session.close();
        }catch (HibernateException e){
            transaction.rollback();
            session.close();
            throw new DBException(e);
        }
    }
}
