package com.test.demo2;

public class B {
    public static int a = 1;
    public static int b = a;

    static {
        a = 2;
    }
}
