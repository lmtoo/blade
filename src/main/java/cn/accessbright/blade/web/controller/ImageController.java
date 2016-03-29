package cn.accessbright.blade.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.accessbright.blade.domain.Image;
import cn.accessbright.blade.domain.Switch;
import cn.accessbright.blade.repository.ImageRepository;

@RestController
@RequestMapping("/images")
public class ImageController {
	@Autowired
	private ImageRepository repository;
	
	
	@RequestMapping("/index")
	public List<Image> images(){
		return repository.findTop10ByOrderByNameDesc();
	}
	
	@RequestMapping("/create")
	public Image create(){
		Image image=new Image();
		image.setName("123");
		image.setUrl("http://www.baidu.com");
		image.setStatus(Switch.ON);
		return repository.save(image);
	}
}
