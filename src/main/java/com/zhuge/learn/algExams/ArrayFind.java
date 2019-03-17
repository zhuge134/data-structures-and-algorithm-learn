package com.zhuge.learn.algExams;

public class ArrayFind {
    public static void main(String[] args) {
        int width = 1, height = 4;
        int[][] matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = i + j;
                System.out.print(" " + (i + j));
            }
            System.out.println();
        }
        long start = System.nanoTime();
        Solution solution = new Solution();
        System.out.println(solution.Find(-1, matrix));
        System.out.println("time cost: " + (System.nanoTime() - start));
        System.out.println(solution.Find(0, matrix));
        System.out.println(solution.Find(4, matrix));
        System.out.println(solution.Find(7, matrix));
        System.out.println(solution.Find(8, matrix));
    }

    public static class Solution {

        public boolean Find(int target, int[][] array) {
            int i = array.length - 1;
            int j = 0;
            while (i >= 0 && j < array[0].length) {
                if (target > array[i][j]) {
                    j++;
                } else if (target < array[i][j]) {
                    i--;
                } else {
                    return true;
                }
            }
            return false;
        }

        public boolean Find2(int target, int[][] array) {
            for (int i = 0; i < array.length; i++) {
                if (target < array[i][0]) {
                    return false;
                }
                if (binarySearch(target, array[i], 0, array[i].length)) {
                    return true;
                }
            }
            return false;
        }

        public boolean binarySearch(int target, int[] array, int start, int end) {
            if ((end - start) < 4) {
                for (int i = start; i < end; i++) {
                    if (target == array[i]) {
                        return true;
                    }
                }
                return false;
            }
            if (target < array[start] || target > array[end - 1]) {
                return false;
            }
            int midIndex = (start + end) / 2;
            if (target == array[midIndex]) {
                return true;
            } else if (target > array[midIndex]) {
                return binarySearch(target, array, midIndex + 1, end);
            } else {
                return binarySearch(target, array, start, midIndex - 1);
            }
        }
    }
}
