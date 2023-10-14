package com.example;

public class MainApp {
    public static void main(String[] args) {
        methodA();
    }

    public static void methodA(){
        methodB();
    }

    public static void methodB(){
        int[] arr = new int[10];
        System.out.println(arr[100]);
    }
}
