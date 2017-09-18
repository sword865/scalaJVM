package com.sword865.scalaJVM.javafile;

/**
 * Created by tianhaowei on 2017/9/18.
 *
 * @author tianhaowei
 * @date 2017/09/18
 */
public class PrintArgs {
    public static void main(String[] args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
