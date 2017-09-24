package com.droux.jtshirt.controller;

import com.droux.jtshirt.controller.form.TshirtForm;
import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.TshirtRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@Controller
@RequestMapping(path="/tshirts")
public class TshirtController {
    private TshirtRepository tshirtRepository;
    private Environment env;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @GetMapping(path="/add")
    public @ResponseBody String addNewTshirt (@RequestParam String name, @RequestParam String color) {
        Tshirt n = new Tshirt();
        n.setName(name);
        n.setColor(color);
        tshirtRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Tshirt> getAllTshirts() {
        logger.info("Colors: " + env.getProperty("tshirt.list.colors"));
        logger.info("Sizes: " + env.getProperty("tshirt.list.sizes"));
        return tshirtRepository.findAll();
    }

    @GetMapping(path="/view")
    public String getTshirt(@RequestParam(required = false) Long id, Model model) {
        if(id != null) {
            model.addAttribute("tshirtForm", new TshirtForm(tshirtRepository.findOne(id)));
        } else {
            model.addAttribute("tshirtForm", new TshirtForm());
        }
        model.addAttribute("colors", Arrays.asList(env.getProperty("tshirt.list.colors").split(",")));
        model.addAttribute("sizes", Arrays.asList(env.getProperty("tshirt.list.sizes").split(",")));
        return "tshirt";
    }

    @PostMapping(path="/save")
    public String saveTshirt(@Valid TshirtForm form, BindingResult result, Model model) {
        logger.info("Saving tshirt #" + form.getId());
        if (result.hasErrors()) {
            model.addAttribute("colors", Arrays.asList(env.getProperty("tshirt.list.colors").split(",")));
            model.addAttribute("sizes", Arrays.asList(env.getProperty("tshirt.list.sizes").split(",")));
            return "tshirt";
        }
        tshirtRepository.save(new Tshirt(form));
        return "redirect:/";
    }

    @GetMapping(path="/delete")
    public String deleteTshirt(@RequestParam Long id) {
        logger.info("Deleting t-shirt #" + id);
        tshirtRepository.delete(id);
        return "tshirt";
    }

    public TshirtController(TshirtRepository tshirtRepository, Environment env) {
        this.tshirtRepository = tshirtRepository;
        this.env = env;
    }
}
