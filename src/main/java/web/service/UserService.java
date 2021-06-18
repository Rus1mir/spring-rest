package web.service;

import web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user, Long[] roles);

    User update(User user, Long[] roles);

    void deleteById(long id);

    Optional<User> findById(long id);

    List<User> findAll();

    Optional<User> findByLogin(String login);

}
