package com.zhuge.learn.algExams;

import java.util.Stack;

public class StackPushPop {

    public static void main(String[] args) {
        int[] pushOrder = {1, 2, 3, 4, 5};
        int[] popOrder = {1, 5, 3, 4, 2};
        System.out.println(new StackPushPop().match(pushOrder, popOrder));
    }

    public boolean match(int[] pushOrder, int[] popOrder) {
        Stack<Integer> stack = new Stack<>();
        int curPushIndex = 0;
        int curPopIndex = 0;
        label:
        while (curPopIndex < popOrder.length) {
            if (!stack.isEmpty() && stack.peek().equals(popOrder[curPopIndex])) {
                stack.pop();
                curPopIndex++;
                continue;
            }
            while (curPushIndex < pushOrder.length) {
                if (pushOrder[curPushIndex] == popOrder[curPopIndex]) {
                    curPopIndex++;
                    curPushIndex++;
                    continue label;
                } else {
                    stack.push(pushOrder[curPushIndex++]);
                }
            }
            return false;
        }
        return true;
    }
}
