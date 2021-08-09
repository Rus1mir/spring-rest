package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.RoleDao;
import web.dao.UserDao;
import web.model.Role;
import web.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    public UserServiceImpl() {
    }

    public void save(User user) {
        userDao.save(user);
    }

    public User update(User user) {
        return userDao.update(user);
    }

    public void deleteById(long id) {
        userDao.deleteById(id);
    }

    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }

}
