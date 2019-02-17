package com.zhuge.learn.dsaa.datastructure.stack;

public class ArrayStack<E> implements Stack<E> {

    /**
     * 存放栈内元素的数组
     */
    private Object[] elements;

    /**
     * 栈容量
     */
    private int capacity;

    /**
     * 栈顶对应的数组下表
     * 当栈为空时，下表为-1
     */
    private int top = -1;

    /**
     * 构造方法
     *
     * @param capacity
     */
    public ArrayStack(int capacity) {
        this.capacity = capacity;
        elements = new Object[capacity];
    }

    /**
     * 入栈
     *
     * @param e
     * @throws ArrayIndexOutOfBoundsException
     */
    public void push(E e) {
        if (++top > capacity) {
            throw new ArrayIndexOutOfBoundsException("element number is bigger than the capacity1");
        }
        elements[top] = e;
    }

    /**
     * 出栈
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public E pop() {
        if (top < 0) {
            return null;
        }
        return (E) elements[top--];
    }
}
