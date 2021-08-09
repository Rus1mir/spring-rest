package web.сontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public AdminController() {
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        Optional<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = this.roleService.findAll();

        if (roles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roles);
    }
}
