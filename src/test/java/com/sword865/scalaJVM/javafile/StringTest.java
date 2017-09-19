package com.sword865.scalaJVM.javafile;

/**
 * Created by tianhaowei on 2017/9/19.
 *
 * @author tianhaowei
 * @date 2017/09/19
 */
public class StringTest {

    public static void main(String[] args) {
        String s1 = "abc1";
        String s2 = "abc1";
        System.out.println(s1 == s2);

        int x = 1;
        String s3 = "abc" + x;
        System.out.println(s1 == s3);

        s3 = s3.intern();
        System.out.println(s1 == s3);
    }

}
