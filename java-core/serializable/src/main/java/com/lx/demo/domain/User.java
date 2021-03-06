package com.lx.demo.domain;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.beans.Transient;
import java.io.Serializable;


/**
 * 序列化对象
 *
 * 如果serialVersionUID不同时
 * java.io.InvalidClassException: com.lx.demo.domain.User; local class incompatible: stream classdesc serialVersionUID = -5388016759828919799, local class serialVersionUID = 2083262352962630020
 * 	at java.io.ObjectStreamClass.initNonProxy(ObjectStreamClass.java:687)
 * 	at java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1883)
 * 	at java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1749)
 * 	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:2040)
 * 	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1571)
 * 	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:431)
 * 	at com.lx.demo.javacore.SerializeDemo.deSerialize(SerializeDemo.java:57)
 * 	at com.lx.demo.javacore.SerializeDemo.main(SerializeDemo.java:22)
 */
public class User implements Serializable {

    private static final long serialVersionUID = 2083262352962630020L;

    public User(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    //不加入注解就不序列化
//    @Protobuf(fieldType = FieldType.UINT64)
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Protobuf(fieldType = FieldType.STRING)
    private String name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 哥哥我不被序列化
     */
    private transient String password;

    public static String staticField = "我是静态变量";
}
