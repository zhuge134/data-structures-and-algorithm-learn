package com.zhuge.learn.algExams;

/**
 * @Title: StrRepalce
 * @Description:
 * @author: zhuge
 * @date: 2019/3/17 19:41
 */
public class StrRepalce {
    public static void main(String[] args) {
        StrRepalce repalce = new StrRepalce();
        System.out.println(repalce.replaceSpace(new StringBuffer("We Are Happy")));
    }

    public String replaceSpace(StringBuffer str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                sb.append('%').append("2").append("0");
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }
}
