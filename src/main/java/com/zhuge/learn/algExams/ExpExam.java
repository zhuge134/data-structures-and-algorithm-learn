package com.zhuge.learn.algExams;

/**
 * @Title: ExpExam
 * @Description:
 * @author: zhuge
 * @date: 2019/3/17 21:01
 */
public class ExpExam {
    public static void main(String[] args) {
        System.out.println(new ExpExam().exp(2.0,10));
    }

    public double exp(double base, int exponent) {
        if (equals(base, 0.0)) {
            return 0.0;
        }
        int absExponent = exponent;
        if (exponent < 0) {
            absExponent = -exponent;
        }
        double res = exp0(base, absExponent);
        if (exponent < 0) {
            res = 1.0 / res;
        }
        return res;
    }

    public double exp0(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent == 1) {
            return base;
        }
        if ((exponent & 1) == 0) {
            //偶数
            return exp0(base, exponent >> 1) * exp0(base, exponent >> 1);
        } else {
            //奇数
            return exp0(base, (exponent - 1) >> 1) * exp0(base, (exponent - 1) >> 1) * base;
        }
    }

    public boolean equals(double d1, double d2) {
        return (d1 - d2) < 0.000001 && (d1 - d2) > -0.000001;
    }
}
