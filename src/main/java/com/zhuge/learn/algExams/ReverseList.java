package com.zhuge.learn.algExams;

public class ReverseList {
    public static void main(String[] args) {
        ListNode<Integer> head = new ListNode<>(1, null, null);
        ListNode<Integer> tail = head;
        for (int i = 0; i < 50; i++) {
            ListNode<Integer> newNode = new ListNode<>(tail.e + 1, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        ListNode<Integer> newHead = new ReverseList().reverse1(head);
        while (newHead != null) {
            System.out.print(" " + newHead.e);
            newHead = newHead.next;
        }
    }

    public <E> ListNode<E> reverse1(ListNode<E> listNode) {
        ListNode<E> cur = listNode;
        while (cur.next != null) {
            ListNode<E> newCur = cur.next;
            ListNode<E> prev = cur.prev;
            cur.next = prev;
            if (null != prev) {
                prev.prev = cur;
            }
            cur = newCur;
        }
        if (cur.prev != null) {
            cur.prev.prev=cur;
            cur.next=cur.prev;
        }
        return cur;
    }

    public <E> ListNode<E> reverse(ListNode<E> listNode) {
        ListNode<E> head = new ListNode(listNode.e, null, null);
        while (listNode.next != null) {
            listNode = listNode.next;
            ListNode newHead = new ListNode(listNode.e, null, head);
            head.prev = newHead;
            head = newHead;
        }
        return head;
    }
}
