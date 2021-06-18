package web.dao;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);

    User update(User user);

    void deleteById(long id);

    Optional<User> findById(long id);

    List<User> findAll();

    Optional<User> findByLogin(String login);
}
