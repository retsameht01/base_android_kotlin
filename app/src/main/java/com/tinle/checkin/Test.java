package com.tinle.checkin;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int[] arr = {77, 93, 52, 100, 44, 1, 10, 82, 99, 5};
        printsum2LargestInteger(arr);
    }

    private static void printsum2LargestInteger(int[] arr) {
        Arrays.sort(arr);
        System.out.println(" 2 Largest sum " + (arr[arr.length-1] + arr[arr.length-2]));
    }
}
