package com.droux.jtshirt.controller.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.droux.jtshirt.controller.exception.TshirtNotFoundException;
import com.droux.jtshirt.controller.storage.StorageService;
import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.TshirtRepository;

@Controller
@RequestMapping(path="/api/tshirts")
public class TshirtApiController {

    private final StorageService storageService;
    private TshirtRepository tshirtRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(path="/all")
    public @ResponseBody List<Tshirt> getAllTshirts() {
        Iterable<Tshirt> tshirts = tshirtRepository.findAll();
        List<Tshirt> list = new ArrayList<>();
        for(Tshirt tshirt: tshirts) {
           tshirt.setImage(imageToBase64(tshirt.getImage()));
           list.add(tshirt);
        }
        return list;
    }

    @GetMapping(path="/view")
    public @ResponseBody Tshirt getTshirt(@RequestParam Long id) {
        Tshirt tshirt = tshirtRepository.findOne(id);
        if(tshirt == null) {
            throw new TshirtNotFoundException("T-shirt " + id + " not found");
        }
        tshirt.setImage(imageToBase64(tshirt.getImage()));
        return tshirt;
    }

    public TshirtApiController(TshirtRepository tshirtRepository, StorageService storageService) {
        this.tshirtRepository = tshirtRepository;
        this.storageService = storageService;
    }

    private String imageToBase64(String filename) {
        File file = null;
        try {
            file = storageService.loadAsResource(filename).getFile();
            return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            logger.error("IOException while getting the image in Base64", e);
        }
        return "";
    }
}
