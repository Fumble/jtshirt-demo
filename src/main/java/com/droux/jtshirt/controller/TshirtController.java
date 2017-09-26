package com.droux.jtshirt.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import javax.activation.MimetypesFileTypeMap;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.droux.jtshirt.controller.form.TshirtForm;
import com.droux.jtshirt.controller.storage.StorageService;
import com.droux.jtshirt.data.bean.Tshirt;
import com.droux.jtshirt.data.repository.TshirtRepository;

@Controller
@RequestMapping(path="/tshirts")
public class TshirtController {
    private static final String IMAGE_FILE = "imageFile";
    private final TshirtRepository tshirtRepository;
    private Environment env;
    private final StorageService storageService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
        logger.info("Saving tshirt #{}", form.getId());
        boolean checkImage = true;
        if(form.getImageFile().getOriginalFilename().isEmpty() && StringUtils.isEmpty(form.getImage())) {
            result.rejectValue(IMAGE_FILE, "error.file.mandatory");
            checkImage = false;
        } else if(form.getId() != null) {
            // We need to check if the user wants to update the image
            if(!StringUtils.isEmpty(form.getImageFile().getOriginalFilename())) {
                Tshirt old = tshirtRepository.findOne(form.getId());
                if (old != null) {
                    checkImage = !old.getImage().equals(form.getImageFile().getOriginalFilename());
                }
            } else {
                checkImage = false;
            }
        }
        // Checking if the uploaded file is an image
        if(checkImage) {
            result = checkimage(result, form);
        }
        if (result.hasErrors()) {
            model.addAttribute("colors", Arrays.asList(env.getProperty("tshirt.list.colors").split(",")));
            model.addAttribute("sizes", Arrays.asList(env.getProperty("tshirt.list.sizes").split(",")));
            return "tshirt";
        }

        if(checkImage) {
            storageService.store(form.getImageFile());
        }
        tshirtRepository.save(new Tshirt(form));
        return "redirect:/";
    }

    @GetMapping(path="/delete")
    public String deleteTshirt(@RequestParam Long id) {
        logger.info("Deleting t-shirt #{}", id);
        tshirtRepository.delete(id);
        return "redirect:/";
    }

    @GetMapping("/getImage")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@RequestParam String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + file.getFilename() + "\"").body(file);
    }

    public TshirtController(TshirtRepository tshirtRepository, Environment env, StorageService storageService) {
        this.tshirtRepository = tshirtRepository;
        this.env = env;
        this.storageService = storageService;
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(env.getProperty("storage.location") + File.separator
                + multipart.getOriginalFilename());
        convFile.createNewFile();
        try (FileOutputStream fos = new FileOutputStream(convFile)){
            fos.write(multipart.getBytes());
        }
        return convFile;
    }

    private boolean isImage(File f) {
        MimetypesFileTypeMap mtftp = new MimetypesFileTypeMap();
        mtftp.addMimeTypes("image png tif jpg jpeg bmp");
        String mimetype = new MimetypesFileTypeMap().getContentType(f);
        logger.info("MimeType: {}", mimetype);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }

    public BindingResult checkimage(BindingResult result, TshirtForm form) {
        form.setImage(form.getImageFile().getOriginalFilename());
        try {
            if(!isImage(multipartToFile(form.getImageFile()))) {
                result.rejectValue(IMAGE_FILE, "error.file.not.image");
            }
        } catch (IOException e) {
            logger.error("IOException while checking the uploaded file", e);
            result.rejectValue(IMAGE_FILE, "error.file.check");
        }
        return result;
    }
}
