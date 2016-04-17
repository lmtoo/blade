package cn.accessbright.blade.repository.test;

import cn.accessbright.blade.domain.test.mongo.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by lile_ on 2016/4/17.
 */
public interface PersonRepository extends MongoRepository<Person, String> {

    Person findByName(String name);

    @Query("{'age':?0}")
    List<Person> withQueryFindByAge(Integer age);
}
