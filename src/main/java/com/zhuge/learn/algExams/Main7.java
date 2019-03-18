package com.zhuge.learn.algExams;

import java.util.ArrayList;

public class Main7 {

    public static void main(String[] args) {
        int[] nums = {1, 5, 6, 2, 1};
        System.out.println(new Main7().solution(nums));
    }

    public int solution(int[] nums) {
        int gradeSum = 0;
        ArrayList<Integer> ordered = new ArrayList<>();
        ordered.add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            int minIncrement = Integer.MAX_VALUE;
            int minIdex = 0;
            for (int j = 0; j < ordered.size() + 1; j++) {
                int pre = j - 1;
                int increment;
                if (pre < 0) {
                    increment = grade(nums[i], ordered.get(j));
                } else if (j == ordered.size()) {
                    increment = grade(nums[i], ordered.get(j - 1));
                } else {
                    increment = grade(nums[i], ordered.get(pre)) + grade(nums[i], ordered.get(j)) - grade(ordered.get(pre), ordered.get(j));
                }
                if (increment < minIncrement) {
                    minIncrement = increment;
                    minIdex = j;
                }
            }
            ordered.add(minIdex, nums[i]);
            gradeSum += minIncrement;
        }
        int maxGrade = 0;
        for (int i = 0; i < ordered.size() - 1; i++) {
            if (grade(ordered.get(i), ordered.get(i + 1)) > maxGrade) {
                maxGrade = grade(ordered.get(i), ordered.get(i + 1));
            }
        }
        return gradeSum - maxGrade;
    }

    public int grade(int i, int j) {
        return Math.abs(i - j);
    }
}
