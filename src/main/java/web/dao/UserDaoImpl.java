package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@EnableJpaRepositories
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private RoleDao roleDao;




    @Override
    public User findByFirstName(String firstName) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        return (User) em.createQuery("SELECT u from User u where u.firstName = :firstName").setParameter("firstName", firstName).getSingleResult();
    }

    @Override
    public void add(User user) {

        user.setPassword(passwordEncoder().encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(1L));
        user.setRoles(roles);

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

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        return em.createQuery("SELECT u from User u").getResultList();

    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(long id, User user) {

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
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        return (User) em.createQuery("SELECT u from User u where u.id = :id").setParameter("id", id).getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void cleanDb() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE from User").executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void removeUserById(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE from User where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        em.getTransaction().commit();
    }


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}