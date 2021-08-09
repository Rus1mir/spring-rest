package web.service;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);

    User update(User user);

    void deleteById(long id);

    Optional<User> findById(long id);

    List<User> findAll();

    Optional<User> findByLogin(String login);

}
