package com.droux.jtshirt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.droux.jtshirt.data.repository.TshirtRepository;

@Controller

public class WelcomeController {
    private final TshirtRepository tshirtRepository;

    @RequestMapping("/")
    public String welcome(Model model) {
        model.addAttribute("tshirtsList", tshirtRepository.findAll());
        return "welcome";
    }

    public WelcomeController(TshirtRepository tshirtRepository) {
        this.tshirtRepository = tshirtRepository;
    }
}
