package com.droux.jtshirt.controller;

import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.TshirtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo")
public class TshirtController {
    @Autowired
    private TshirtRepository tshirtRepository;

    @GetMapping(path="/add") // Map ONLY GET Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String color) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Tshirt n = new Tshirt();
        n.setName(name);
        n.setColor(color);
        tshirtRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Tshirt> getAllUsers() {
        // This returns a JSON or XML with the users
        return tshirtRepository.findAll();
    }
}
