package cn.accessbright.blade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import cn.accessbright.blade.domain.questions.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	List<Image> findTop10ByOrderByNameDesc();
}
