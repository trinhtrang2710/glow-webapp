package trangtt.glow.service;

import org.springframework.stereotype.Service;
import trangtt.glow.model.UpdateUser;
import trangtt.glow.model.User;

import java.util.List;

@Service
public interface UserService {
    User findByUserById(long id);

    User findUserByName(String userName);

    List<User> findAllUsers();

    User save(User user);

    void delete(long id);

    User updateUser(Long id, User user);

    User partialUpdateUser(Long id, UpdateUser user);
}
