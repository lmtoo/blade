package cn.accessbright.blade.web.controller.test;

import cn.accessbright.blade.domain.test.mongo.Location;
import cn.accessbright.blade.domain.test.mongo.Person;
import cn.accessbright.blade.repository.test.PersonDao;
import cn.accessbright.blade.repository.test.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by lile_ on 2016/4/17.
 */
@RestController
public class DataController {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/save")
    public Person save() {
        Person p = new Person("wyf", 32);

        Collection<Location> locations = new LinkedHashSet<>();

        Location loc1 = new Location("上海", "2009");
        Location loc2 = new Location("合肥", "2010");
        Location loc3 = new Location("广州", "2011");
        Location loc4 = new Location("马鞍山", "2012");

        locations.add(loc1);
        locations.add(loc2);
        locations.add(loc3);
        locations.add(loc4);

        p.setLocations(locations);

        return personRepository.save(p);
    }

    @RequestMapping("/q1")
    public Person q1(String name) {
        return personRepository.findByName(name);
    }

    @RequestMapping("/q2")
    public List<Person> q2(Integer age) {
        return personRepository.withQueryFindByAge(age);
    }

    @RequestMapping("/set")
    public void set() {
        cn.accessbright.blade.domain.test.redis.Person person = new cn.accessbright.blade.domain.test.redis.Person("1", "wyf", 32);
        personDao.save(person);
        personDao.stringRedisTemplateDemo();
    }

    @RequestMapping("/getStr")
    public String getStr() {
        return personDao.getString();
    }

    @RequestMapping("/getPerson")
    public cn.accessbright.blade.domain.test.redis.Person getPerson() {
        return personDao.getPerson();
    }
}
