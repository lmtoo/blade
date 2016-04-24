package cn.accessbright.blade.repository;

import cn.accessbright.blade.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * 自定义的查询
 *
 * Created by lile_ on 2016/4/24.
 */
@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    @Autowired
    private EntityManager entityManager;

    @Override
    public Integer findIdByUsername(String username) {
        Query query = entityManager.createQuery("select u from User u where u.username=?1");
        query.setParameter(1, username);
        User user = (User) query.getSingleResult();
        return user.getId();
    }
}
