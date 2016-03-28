package cn.accessbright.blade.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import cn.accessbright.blade.domain.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {

	List<Image> findTop10ByOrderByNameDesc();
}
