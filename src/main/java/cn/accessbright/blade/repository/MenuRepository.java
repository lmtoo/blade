package cn.accessbright.blade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.accessbright.blade.domain.system.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findByParentId(String parentId);

    // 查找一级菜单
    List<Menu> findByParentIsNull();
}
