package cn.accessbright.blade.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.accessbright.blade.domain.ChapterProgress;
import cn.accessbright.blade.repository.ChapterProgressRepository;

@RestController
@RequestMapping("/chapter/progresses")
public class ChapterProgressController {

	@Autowired
	private ChapterProgressRepository repository;

	@RequestMapping("/top10")
	public List<ChapterProgress> list() {
		return repository.findTop10ByOrderByNameDesc();
	}

	@RequestMapping("/create")
	public ChapterProgress create() {
		ChapterProgress progress = new ChapterProgress();
		progress.setName("第二阶段");
		return repository.save(progress);
	}
}
