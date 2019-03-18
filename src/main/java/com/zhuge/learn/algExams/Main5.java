package com.zhuge.learn.algExams;

/**
 * @Title: 贪心算法
 * @Description:
 * @author: zhuge
 * @date: 2019/3/18 23:20
 */
public class Main5 {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        System.out.println(new Main5().solution(nums));
    }

    public boolean solution(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int j = i + 1;
            for (; j < nums.length; j++) {
                if ((nums[i] * nums[j]) % 4 == 0) {
                    //交换i+1和j
                    int tmp = nums[i + 1];
                    nums[i + 1] = nums[j];
                    nums[j] = tmp;
                    break;
                }
            }
            if (j == nums.length) {
                //没找到，可以判定不符合条件
                return false;
            }
        }
        return true;
    }

}
