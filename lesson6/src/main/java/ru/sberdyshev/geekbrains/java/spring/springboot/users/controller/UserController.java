package ru.sberdyshev.geekbrains.java.spring.springboot.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.domain.User;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.dto.UserDto;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.service.RoleService;
import ru.sberdyshev.geekbrains.java.spring.springboot.users.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/users/{user-id}/details")
    public String getUserById(
            @PathVariable(name = "user-id") Long userId,
            Model model) {
        log.debug("Called GET /users/{}/details", userId);
        Optional<Long> optionalUserId = Optional.ofNullable(userId);
        User resultUser = (userService.findById(optionalUserId)).get();
        UserDto userDto = new UserDto(
                resultUser.getId(),
                resultUser.getName(),
                resultUser.getPassword(),
                resultUser.getPassword(),
                resultUser.getRoles());
        log.debug("Called GET /users/{}/details, model userDto - \"{}\"", userId, model.getAttribute("user"));
        log.debug("Called GET /users/{}/details, found userDto - \"{}\"", userId, userDto);
        model.addAttribute("user", userDto);
        model.addAttribute("roles", (roleService.getAllRoles()));
        log.debug("Called GET /users/{}/details, model userDto - \"{}\"", userId, model.getAttribute("user"));
        return "user_details";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/users/details")
    public String getUserByName(@RequestParam(name = "user-name") String userName, Model model) {
        log.debug("Called GET /users/details with param name \"{}\"", userName);
        Optional<String> optionalUserName = Optional.ofNullable(userName);
        User resultUser = (userService.findByName(optionalUserName)).get();
        UserDto userDto = new UserDto(
                resultUser.getId(),
                resultUser.getName(),
                resultUser.getPassword(),
                resultUser.getPassword(),
                resultUser.getRoles());
        model.addAttribute("user", userDto);
        model.addAttribute("roles", (roleService.getAllRoles()));
        return "user_details";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/users")
    public String getUserList(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize,
                              Model model) {
        log.debug("Called GET /users with pageNumber \"{}\", pageSize \"{}\"", pageNumber, pageSize);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        Optional<Integer> optionalPageNumber = Optional.ofNullable(pageNumber);
        Optional<Integer> optionalPageSize = Optional.ofNullable(pageSize);
        model.addAttribute("userPage", userService.getAllUsersByPage(optionalPageNumber, optionalPageSize));
        return "user_list";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/users/new")
    public String newUser(Model model) {
        log.debug("Called GET /users/new");
        model.addAttribute("user", new UserDto());
        model.addAttribute("roles", (roleService.getAllRoles()));
        return "user_add";
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/users")
    public String manageUser(@RequestParam(value = "_method", required = false) String method,
                          @Valid UserDto userDto,
                          BindingResult bindingResult,
                          Model model) {
        log.debug("manageUser() - Called POST /users, method \"{}\"", method);
        if (bindingResult.hasErrors()) {
            log.debug("manageUser() - bindingResult has errors, method - \"{}\"", method);
            if (method == "put" || method == "delete") {
                model.addAttribute("user", userDto);
                model.addAttribute("roles", (roleService.getAllRoles()));
                return "user_details";
            }
            model.addAttribute("user", new UserDto());
            model.addAttribute("roles", (roleService.getAllRoles()));
            return "user_add";
        }
        if (method != "delete" && !userDto.getPassword().equals(userDto.getMatchingPassword())) {
            log.debug("manageUser() - password and matching password are unequal, method - \"{}\"", method);
            bindingResult.rejectValue("password", "", "Password not matching");
            if (method == "put") {
                model.addAttribute("user", userDto);
                model.addAttribute("roles", (roleService.getAllRoles()));
                return "user_details";
            }
            model.addAttribute("user", new UserDto());
            model.addAttribute("roles", (roleService.getAllRoles()));
            return "user_add";
        }
        User user = new User(
                userDto.getId(),
                userDto.getName(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getRoles());
        log.debug("manageUser() - new user - \"{}\"", user);
        if (method == null) {
            userService.insert(user);
        } else {
            switch (method) {
                case "put":
                    userService.update(user);
                    break;
                case "delete":
                    userService.delete(Optional.ofNullable(userDto.getId()));
                    break;
                default:
                    userService.insert(user);
            }
        }
        return "redirect:/users";
    }
}
