package com.zhuge.learn.algExams;

public class ListNode<E> {
    ListNode<E> prev;
    ListNode<E> next;
    E e;

    public ListNode(E e, ListNode<E> prev, ListNode<E> next) {
        this.next = next;
        this.prev = prev;
        this.e = e;
    }
}
