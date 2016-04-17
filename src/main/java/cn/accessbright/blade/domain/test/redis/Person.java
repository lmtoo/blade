package cn.accessbright.blade.domain.test.redis;

import java.io.Serializable;

/**
 * Created by lile_ on 2016/4/17.
 */
public class Person implements Serializable {

    private String id;

    private String name;

    private Integer age;

    public Person(String id, String name, Integer age) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Person() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
