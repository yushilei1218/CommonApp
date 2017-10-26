package com.yushilei.commonapp.common.bean.db;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;

import java.util.Date;
import java.util.List;

/**
 * @author shilei.yu
 * @since 2017/10/26
 */
@Entity
public class Person {
    @Id
    @Property(nameInDb = "ID")
    private Long id;

    @Property(nameInDb = "NAME")
    private String name;

    @Property(nameInDb = "Age")
    private int age;

    private Date mDate;

    @Convert(converter = PersonTypeConverter.class, columnType = String.class)
    private List<String> tags;

    @Transient
    private int nothing;

    @Generated(hash = 1166189082)
    public Person(Long id, String name, int age, Date mDate, List<String> tags) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.mDate = mDate;
        this.tags = tags;
    }

    public Person(String name, int age, Date date, List<String> tags, int nothing) {
        this.name = name;
        this.age = age;
        mDate = date;
        this.tags = tags;
        this.nothing = nothing;
    }

    @Generated(hash = 1024547259)
    public Person() {
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getNothing() {
        return nothing;
    }

    public void setNothing(int nothing) {
        this.nothing = nothing;
    }

    public Date getMDate() {
        return this.mDate;
    }

    public void setMDate(Date mDate) {
        this.mDate = mDate;
    }
}
