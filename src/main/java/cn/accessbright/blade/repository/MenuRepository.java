package cn.accessbright.blade.repository;

<<<<<<< HEAD
import cn.accessbright.blade.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
=======
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
>>>>>>> 18678fcb40d49d049c02de54468fd5f3856b2c83

import java.util.List;


public interface MenuRepository extends JpaRepository<Menu, Integer> {

    List<Menu> findByParentId(String parentId);

    // 查找一级菜单
    List<Menu> findByParentIsNull();
}
