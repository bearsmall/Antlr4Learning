package com.test.demo2;

public class A {
    public static int a = 1;
    static {
        a = 2;
    }
    public static int b = a;
}
