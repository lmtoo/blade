package cn.accessbright.blade.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

    @Autowired
    private MessageSource messageSource;

    @ResponseBody
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile file, Locale locale) {
        System.out.println(file.getOriginalFilename());
        try {
            FileCopyUtils.copy(file.getBytes(), new File("H:/" + file.getOriginalFilename()));
            return "ok";
        } catch (IOException e) {
            e.printStackTrace();
            String message=messageSource.getMessage("upload.io", new Object[]{e.getMessage()},locale);
            String message1=messageSource.getMessage("upload.io", new Object[]{e.getMessage()}, new Locale("en","US"));
            return  message1;
        }
    }
}
