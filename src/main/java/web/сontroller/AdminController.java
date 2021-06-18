package web.сontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping({"/all_users", "/"})
    public String showAllUsers(ModelMap model) {
        model.addAttribute("listUsers", userService.findAll());
        return "all_users";
    }

    @GetMapping("/newuser")
    public String showAddForm(ModelMap model, User user) {
        model.addAttribute("allRoles", roleService.findAll());
        return "add_user";
    }


    @PostMapping("/save")
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

    @GetMapping(value = "/edit")
    public String showUpdateForm(@RequestParam("id") long id, Model model) {
        User user = userService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roleService.findAll());
        return "update_user";
    }

    @GetMapping(value = "/delete")
    public String deleteUsers(@RequestParam("id") int id) {
        userService.deleteById(id);
        return "redirect:/admin/";
    }
}
