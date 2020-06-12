package com.liuruichao.boot2;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * User
 *
 * @author liuruichao
 * Created on 2018/4/13 13:32
 */
public class User implements Serializable {
    private Long id;

    private String name;

    private String name2;

    private String name3;

    private int age;

    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName2() {
        return name2;
    }

    public User setName2(String name2) {
        this.name2 = name2;
        return this;
    }

    public String getName3() {
        return name3;
    }

    public User setName3(String name3) {
        this.name3 = name3;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
