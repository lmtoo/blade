package cn.accessbright.blade.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.accessbright.blade.domain.ChapterProgress;

public interface ChapterProgressRepository extends CrudRepository<ChapterProgress, Integer> {

	List<ChapterProgress> findTop10ByOrderByNameDesc();

}
