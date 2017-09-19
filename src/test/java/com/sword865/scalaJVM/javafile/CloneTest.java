package com.sword865.scalaJVM.javafile;

/**
 * Created by tianhaowei on 2017/9/19.
 *
 * @author tianhaowei
 * @date 2017/09/19
 */

public class CloneTest implements Cloneable {

    private double pi = 3.14;

    @Override
    public CloneTest clone() {
        try {
            return (CloneTest) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        CloneTest obj1 = new CloneTest();
        CloneTest obj2 = obj1.clone();
        obj1.pi = 3.1415926;
        System.out.println(obj1.pi);
        System.out.println(obj2.pi);
    }

}

