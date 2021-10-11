package web.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import web.model.User;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;




//    @Autowired
//    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        //sessionFactory.getCurrentSession().save(user);
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
        em.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        /*TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();*/
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        return em.createQuery("SELECT u from User u").getResultList();

    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(long id, User user) {
        /*sessionFactory.getCurrentSession().createQuery("update User set firstName = :firstName, lastName = :lastName, email = :email where id = :id")
                .setParameter("firstName", user.getFirstName()).setParameter("lastName", user.getLastName()). setParameter("email", user.getEmail())
                .setParameter("id", id).executeUpdate();*/
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("UPDATE User u " +
                "SET u.firstName = :firstName, u.lastName = :lastName, u.email = :email WHERE u.id = :id")
                .setParameter("firstName", user.getFirstName())
                .setParameter("lastName", user.getLastName())
                .setParameter("email", user.getEmail())
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserById(Long id) {
        // return (User) sessionFactory.getCurrentSession().createQuery("from User where id = :id").setParameter("id", id).uniqueResult();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        return (User) em.createQuery("SELECT u from User u where u.id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void cleanDb() {
        // sessionFactory.getCurrentSession().createQuery("delete from User").executeUpdate();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE from User").executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeUserById(long id) {
        // sessionFactory.getCurrentSession().createQuery("delete User where id = :id").setParameter("id", id).executeUpdate();
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();
    }



}