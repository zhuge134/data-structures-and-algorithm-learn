package com.zhuge.learn.algExams;

public class Fibonacci {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        new Fibonacci().fn(40);
        System.out.println("time cost: " + (System.currentTimeMillis() - start));

        long start2=System.currentTimeMillis();
        new Fibonacci().fn2(40);
        System.out.println("fn2 time cost: "+(System.currentTimeMillis()-start2));
    }

    public int fn2(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return fn2(n - 1) + fn2(n - 2);
    }

    public int fn(int n) {
        int cur = 1, next = 1;
        for (int i = 3; i <= n; i++) {
            int newOne = cur + next;
            cur = next;
            next = newOne;
            //System.out.println(next);
        }
        return next;
    }
}
