package com.zhuge.learn.algExams;

public class Main2 {

    public static void main(String[] args) {
        System.out.println(new Main2().solution(12340));
    }

    public int solution(int n) {
        String str = String.valueOf(n);
        String reverse = new StringBuilder(str).reverse().toString();
        StringBuilder sum = new StringBuilder();
        //进位
        int mark = 0;
        for (int i = 0; i < str.length(); i++) {
            int raw = Integer.parseInt(str.substring(i, i + 1));
            int rever = Integer.parseInt(reverse.substring(i, i + 1));
            int tmpSum = raw + rever + mark;
            if(tmpSum<10){
                mark=0;
                sum.append(String.valueOf(tmpSum));
            }else {
                mark=1;
                sum.append(String.valueOf(tmpSum-10));
            }
        }
        return Integer.parseInt(sum.reverse().toString());
    }
}
