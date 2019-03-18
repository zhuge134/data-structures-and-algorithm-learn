package com.zhuge.learn.algExams;

public class Main3 {
    public static void main(String[] args) {
        System.out.println(new Main3().solution("aaabbaaac"));
    }

    public double solution(String s) {
        char c = s.charAt(0);
        int curSubStrNum = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == c) {
            } else {
                c = s.charAt(i);
                curSubStrNum++;
            }
        }
        curSubStrNum++;
        return (double) s.length() /curSubStrNum;
    }
}
