package cn.accessbright.blade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import cn.accessbright.blade.domain.ChapterProgress;

public interface ChapterProgressRepository extends JpaRepository<ChapterProgress, Integer> {

	List<ChapterProgress> findTop10ByOrderByNameDesc();

}
