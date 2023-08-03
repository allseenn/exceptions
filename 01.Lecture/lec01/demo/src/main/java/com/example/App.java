package com.example;

import java.io.File;

public class App 
{
    public static void main( String[] args )

    {   // returns Error code -1
        // System.out.println(getFileSize(new File("/media/Rostislav/64/github/exeptions/01.Lecture/lec01/demo/src/main/java/com/example/App.java")));

        // ArithmeticException
        // System.out.println(divide(10, 0));

        // ArrayIndexOutOfBoundsException
        // int[] ints = new int[10];
        // System.out.println(ints[1000]);

        // Stack Trace
        //a();

        // Бросаем собственные исключения в RuntimeException
        div(10, 0);
    }
    Array
    public static long getFileSize(File file)
    {
        if(!file.exists()){
            return -1;
        }
        return file.length();
    }
    public static int divide(int a1, int a2){
        if(a2==0){
            return -1;
        }
        return a1/a2;
    }
    public static void a(){
        b();
    }
    public static void b(){
        c();
    }
    public static void c(){
        int[] ints = new int[10];
        System.out.println(ints[1000]);
    }

    public static int div(int a1, int a2){
        if(a2==0){
            throw new RuntimeException("Нельзя делить на 0");
        }
        return a1/a2;
    }

}
