package trangtt.glow.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import trangtt.glow.model.UpdateUser;
import trangtt.glow.model.User;
import trangtt.glow.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/admin/users")
@PreAuthorize("hasRole('ADMIN')")
@Slf4j
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/")
    public List<User> getAllUsers() {
        log.info("Retrieving all users");
        return userService.findAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable(value = "id") Long id) {
        log.info("Retrieving user for given user id: " + id);
        return userService.findByUserById(id);
    }

    @PostMapping(value = "/")
    public User createUser(@Valid @RequestBody User user) {
        log.info("Creating user");
        return userService.save(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User user) {
        log.info("Updating user for given user id:" + id);
        User appUser = userService.updateUser(id, user);
        return ok(appUser);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<User> updateUserRole(@Validated @RequestBody UpdateUser partialUserUpdate, @PathVariable(value = "id") Long id) {
        log.info("Updating roles for given user id:" + id);
        User appUser = userService.partialUpdateUser(id, partialUserUpdate);
        return ok(appUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Long id) {
        log.info("Deleting given user id:" + id);
        userService.delete(id);
        return ok("Successfully deleted user");
    }

}
