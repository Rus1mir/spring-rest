package web.сontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String showAllUsers(ModelMap model, @AuthenticationPrincipal User user) {
        model.addAttribute("currentUser", user);
        model.addAttribute("listUsers", userService.findAll());
        model.addAttribute("allRoles", roleService.findAll());
        return "users";
    }

    @PostMapping("/")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("chosenRoles") Long[] roles) {
        userService.save(user, roles);
        return "redirect:/admin/";
    }

    @PostMapping("/update")
    public String updateUser(User user,@RequestParam("id") Long id, @RequestParam("chosenRoles") Long[] roles) {
        user.setId(id);
        userService.update(user, roles);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/delete")
    public String deleteUsers(@RequestParam("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }
}
