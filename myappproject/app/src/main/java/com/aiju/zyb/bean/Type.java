package com.aiju.zyb.bean;

/**
 * Created by AIJU on 2017-05-02.
 */

public class Type {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String typename;
    private String name;
    public  Type(int id,String typename,String name)
    {
        this.id=id;
        this.typename=typename;
        this.name=name;
    }
}
