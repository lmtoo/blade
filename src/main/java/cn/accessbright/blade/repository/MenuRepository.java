package cn.accessbright.blade.repository;

import java.util.List;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.EntityGraph;
=======
import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> 77f0d59ab4a6637b4325023adbdbe80d3a8e3600
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.accessbright.blade.domain.system.Menu;

<<<<<<< HEAD
public interface MenuRepository extends PagingAndSortingRepository<Menu, Integer> {
=======
public interface MenuRepository extends JpaRepository<Menu, Integer> {
	
	List<Menu> findByParentId(String parentId);
>>>>>>> 77f0d59ab4a6637b4325023adbdbe80d3a8e3600

    List<Menu> findByParentId(String parentId);

    // 查找一级菜单
    List<Menu> findByParentIsNull();
}
