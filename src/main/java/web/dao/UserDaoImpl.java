package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(User user) {
        entityManager.persist(user);
    }

    public User update(User user) {
        return entityManager.merge(user);
    }

    public void deleteById(long id) {
        Query query = entityManager.createQuery("DELETE FROM User u WHERE u.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Optional<User> findById(long id) {
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, id));
        return user;
    }

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public Optional<User> findByLogin(String login) {
        Query query = entityManager.createQuery("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :login", User.class);
        query.setParameter("login", login);
        Optional<User> user = Optional.ofNullable((User) query.getResultStream().findFirst().orElse(null));
        return user;
    }
}
