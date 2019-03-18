package com.zhuge.learn.algExams;

import java.util.Scanner;

public class Main1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int i=scanner.nextInt();
        System.out.println(new Main1().solution(i));
    }

    public String solution(int n) {
        int curN = n;
        StringBuilder sb = new StringBuilder();
        while (curN > 0) {
            if ((curN & 1) == 0) {
                //偶数
                curN = (curN >> 1) - 1;
                sb.append("2");
            } else {
                //奇数
                curN = (curN - 1) >> 1;
                sb.append("1");
            }
        }
        return sb.reverse().toString();
    }
}
