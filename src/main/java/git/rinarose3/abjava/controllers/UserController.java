package git.rinarose3.abjava.controllers;

import git.rinarose3.abjava.models.User;
import git.rinarose3.abjava.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> findAllUser(){
        return userService.findAllUsers();
    }

    @GetMapping("/{name}")
    public Optional<User> findUserByName(@PathVariable String name) {
        return userService.findUserByName(name);
    }
    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
