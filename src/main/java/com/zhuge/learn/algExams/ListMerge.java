package com.zhuge.learn.algExams;

/**
 * @Title: ListMerge
 * @Description:
 * @author: zhuge
 * @date: 2019/3/17 22:21
 */
public class ListMerge {

    public static void main(String[] args) {
        ListNode<Integer> head1 = new ListNode<>(1, null, null);
        ListNode<Integer> tail1 = head1;
        for (int i = 0; i < 25; i++) {
            ListNode<Integer> newNode = new ListNode<>(tail1.e + 2, tail1, null);
            tail1.next = newNode;
            tail1 = newNode;
        }

        ListNode<Integer> head2 = new ListNode<>(0, null, null);
        ListNode<Integer> tail2 = head2;
        for (int i = 0; i < 25; i++) {
            ListNode<Integer> newNode = new ListNode<>(tail2.e + 2, tail2, null);
            tail2.next = newNode;
            tail2 = newNode;
        }

        ListNode<Integer> merged = new ListMerge().merger(head1, head2);
        for (; merged != null; merged = merged.next) {
            System.out.print(" "+merged.e);
        }
    }

    public <E extends Comparable<E>> ListNode<E> merger(ListNode<E> list1, ListNode<E> list2) {
        ListNode<E> p1 = list1;
        ListNode<E> p2 = list2;
        ListNode<E> head = null;
        ListNode<E> tail = null;
        if (compare(p1, p2) < 0) {
            head = new ListNode<>(p1.e, null, null);
            tail = head;
            p1 = p1.next;
        } else {
            head = new ListNode<>(p2.e, null, null);
            tail = head;
            p2 = p2.next;
        }
        while (p1 != null && p2 != null) {
            if (compare(p1, p2) < 0) {
                ListNode<E> newNode = new ListNode<>(p1.e, tail, null);
                tail.next = newNode;
                tail = newNode;
                p1 = p1.next;
            } else {
                ListNode<E> newNode = new ListNode<>(p2.e, tail, null);
                tail.next = newNode;
                tail = newNode;
                p2 = p2.next;
            }
        }
        if (null != p1) {
            while (p1 != null) {
                ListNode<E> newNode = new ListNode<>(p1.e, tail, null);
                tail.next = newNode;
                tail = newNode;
                p1 = p1.next;
            }
        }
        if (null != p2) {
            while (p2 != null) {
                ListNode<E> newNode = new ListNode<>(p2.e, tail, null);
                tail.next = newNode;
                tail = newNode;
                p2 = p2.next;
            }
        }
        return head;
    }

    public <E extends Comparable<E>> int compare(ListNode<E> l1, ListNode<E> l2) {
        return l1.e.compareTo(l2.e);
    }
}
