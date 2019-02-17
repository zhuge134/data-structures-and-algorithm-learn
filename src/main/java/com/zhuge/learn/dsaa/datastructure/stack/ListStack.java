package com.zhuge.learn.dsaa.datastructure.stack;

import java.util.LinkedList;

public class ListStack<E> implements Stack<E> {

    private LinkedList<E> elements = new LinkedList<E>();

    public ListStack() {
    }

    public void push(E e) {
        elements.addLast(e);
    }

    public E pop() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.removeLast();
    }
}
