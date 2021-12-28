package app.dao;

import app.model.User;

import java.util.List;

public interface UserDAO {
    Integer add(User user);
    User findByEmail(String email);
    User findById(int id);
    List<User> findAll();
    User edit(User user);
}
