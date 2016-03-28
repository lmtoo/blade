package cn.accessbright.blade.repository;

import org.springframework.data.repository.CrudRepository;

import cn.accessbright.blade.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
