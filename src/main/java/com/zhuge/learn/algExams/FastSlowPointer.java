package com.zhuge.learn.algExams;

/**
 * @Title: FastSlowPointer
 * @Description:
 * @author: zhuge
 * @date: 2019/3/17 21:32
 */
public class FastSlowPointer {
    public static void main(String[] args) {
        ListNode<Integer> head = new ListNode<>(1, null, null);
        ListNode<Integer> tail = head;
        for (int i = 0; i < 50; i++) {
            ListNode<Integer> newNode = new ListNode<>(tail.e+1, tail, null);
            tail.next = newNode;
            tail=newNode;
        }
        System.out.println(new FastSlowPointer().findLastKth(head,10).e);
    }

    public ListNode findLastKth(ListNode listNode, int k) {
        ListNode fast = listNode;
        ListNode slow = listNode;
        int fastSteps = 0;
        while (true) {
            if (fastSteps == k - 1) {
                break;
            }
            fast = fast.next;
            fastSteps++;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
