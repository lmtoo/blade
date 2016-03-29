package cn.accessbright.blade.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.accessbright.blade.domain.system.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

}
