package cn.accessbright.blade.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.accessbright.blade.domain.system.Role;
import cn.accessbright.blade.domain.system.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
	User findUserByUsername(String username);

	@Query("select u.roles from User u where u.username=:username")
	@EntityGraph(attributePaths = { "roles.menus", "roles.funcs" }, type = EntityGraphType.LOAD)
	List<Role> findRolesByUsername(String username);
}
