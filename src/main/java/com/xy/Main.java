package com.xy;

public class Main {

    public static int a(int b){
        return b+1;
    }

    public static void main(String[] args) {
        int a = 1;
        a = a(1);
        System.out.println(a);
    }
}
