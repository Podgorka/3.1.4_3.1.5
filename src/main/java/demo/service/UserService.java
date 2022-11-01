package demo.service;
import demo.model.User;

import java.util.List;

public interface UserService {

    List<User> index();

    User showUser(long id);

    User showUserByUsername(String email);

    boolean saveUser(User user);

    void updateUser(User user);

    void deleteUser(long id);

}
