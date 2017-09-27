package com.droux.jtshirt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.droux.jtshirt.data.repository.TshirtRepository;

@Controller

public class WelcomeController {
    private final TshirtRepository tshirtRepository;

    @RequestMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("tshirtsList", tshirtRepository.findAll());
        return "welcome";
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = {"/", "/index"})
    public String getRoot() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String postLogin() {
        return "redirect:/login-error";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    public WelcomeController(TshirtRepository tshirtRepository) {
        this.tshirtRepository = tshirtRepository;
    }
}
