package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(Model model) {
        List<User> users = userService.listUsers();
        model.addAttribute("people", users);
        return "people/index";
    }

    @GetMapping("/{id}/edit")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("person", userService.getUserById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") User user, @PathVariable("id") long id) {
        userService.update(id, user);
        return "redirect:index";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:index";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("person", new User());
        return "people/new";
    }

    @PostMapping()
    public String add(@ModelAttribute("person") User user) {
        userService.add(user);
        return "redirect:people/index";
    }

}
