package trangtt.glow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import trangtt.glow.model.UpdateUser;
import trangtt.glow.model.User;
import trangtt.glow.exception.UserNotFoundException;
import trangtt.glow.repository.UserRepository;
import trangtt.glow.service.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder bcryptEncoder;

    public UserServiceImpl(BCryptPasswordEncoder bcryptEncoder) {
        this.bcryptEncoder = bcryptEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleType()));
        });
        return authorities;
    }

    @Override
    public User findByUserById(long id) {
        return  userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found on :: " + id));
    }

    @Override
    public User findUserByName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id :" + id));
        userRepository.delete(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User user1 = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id :" + id));
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setPassword(bcryptEncoder.encode(user.getPassword()));
        user1.setRoles(user.getRoles());
        user1.setUsername(user.getUsername());
        user1.setAddresses(user.getAddresses());
        final User appUser = userRepository.save(user1);
        return appUser;
    }

    @Override
    public User partialUpdateUser(Long id, UpdateUser user) {
        User user1 = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found for id :" + id));
        user1.setRoles(user.getRoles());
        final User appUser = userRepository.save(user1);
        return appUser;
    }
}
