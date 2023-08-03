package ru.gb;

import java.util.Arrays;

public class Answer {
    public static void main(String[] args) {
        int [] a = new int[]{12,8,16};
        int [] b = new int[]{4,2,1,5};
        int [] c = divArrays(a, b);
        System.out.printf(Arrays.toString(c));
    }
    public static int[] divArrays(int[] a, int[] b){
        if(a.length != b.length){
            return new int[1];
        }
        int [] c = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            if(b[i]==0){
                throw new RuntimeException("Нельзя делить на 0");
            }
            c[i] = a[i] / b[i];
        }
        return c;
    }
}
