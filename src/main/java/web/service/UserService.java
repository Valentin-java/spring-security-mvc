package web.service;

import web.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void update(long id, User user);
    List<User> listUsers();
    User getUserById(Long id);
    void cleanDb();
    void removeUserById(long id);
    User findByFirstName(String firstName);
}
