package web.сontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import web.model.User;
import web.service.UserService;

import java.security.Principal;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "user/userpage")
    public String UserPage(ModelMap model, Principal principal) {
        User user = userService.findByLogin(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("User with login: " + principal.getName() + " was not found"));
        model.addAttribute("user", user);
        return "userpage";
    }

}
