package trangtt.glow.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import trangtt.glow.exception.ForbiddenException;
import trangtt.glow.model.User;
import trangtt.glow.service.UserService;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity currentUser(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a -> a.getAuthority())
                .collect(toList())
        );
        return ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String  userName  = userDetails.getUsername();
        User user = userService.findUserByName(userName);

        if(user != null && id.equals(user.getId())) {
            userService.delete(id);
        } else {
            log.error("You can delete only your own profile");
            throw new ForbiddenException(""+id);
        }
        return ok("Successfully deleted user");
    }


}
