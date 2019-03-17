package com.zhuge.learn.algExams;

/**
 * @Title: OddEvenOrder
 * @Description:
 * @author: zhuge
 * @date: 2019/3/17 21:16
 */
public class OddEvenOrder {

    public static void main(String[] args) {
        int[] nums = {1, 2, 564, 2, 34, 4, 3, 6, 321, 65, 8};
        int[] newnums = new OddEvenOrder().order(nums);
        for (int i = 0; i < newnums.length; i++) {
            System.out.print(" "+newnums[i]);
        }
    }

    public int[] order(int[] array) {
        int[] res = new int[array.length];
        int oddNum = 0;
        for (int i = 0; i < array.length; i++) {
            if (isOdd(array[i])) {
                oddNum++;
            }
        }
        int oddIndex = 0;
        int evenIndex = oddNum;
        for (int i = 0; i < array.length; i++) {
            if (isOdd(array[i])) {
                res[oddIndex++] = array[i];
            } else {
                res[evenIndex++] = array[i];
            }
        }
        return res;
    }

    /**
     * 是否为奇数
     *
     * @param i
     * @return
     */
    public boolean isOdd(int i) {
        return (i & 1) == 1;
    }
}
