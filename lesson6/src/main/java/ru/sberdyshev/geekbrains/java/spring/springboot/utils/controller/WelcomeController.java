package ru.sberdyshev.geekbrains.java.spring.springboot.utils.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    private static final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @GetMapping("/welcome")
    public String welcome(Model model) {
        logger.debug("Called /welcome");
        model.addAttribute("helloText", "Hi from MVC controller!!!");
        return "welcome";
    }
}
