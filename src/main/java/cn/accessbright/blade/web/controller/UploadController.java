package cn.accessbright.blade.web.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	@RequestMapping(value = "toUpload")
	public String toUpload() {
		return "upload";
	}

	@ResponseBody
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam MultipartFile file) {
		System.out.println(file.getOriginalFilename());
		try {
			FileCopyUtils.copy(file.getBytes(), new File("d:/" + file.getOriginalFilename()));
			return "ok";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
	}

}
