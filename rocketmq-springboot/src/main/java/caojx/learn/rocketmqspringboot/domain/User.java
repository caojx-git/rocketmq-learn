package caojx.learn.rocketmqspringboot.domain;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author caojx created on 2022/5/14 11:19 AM
 */
public class User implements Serializable {

    private String name;

    private Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
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

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
