package cn.accessbright.blade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.accessbright.blade.domain.system.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
