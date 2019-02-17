package com.zhuge.learn.dsaa.datastructure.stack;

/**
 * @Title: Stack
 * @Description:
 * @author: zhuge
 * @date: 2019/2/17 19:35
 * @Param: E 元素类型
 */
public interface Stack<E> {

    /**
     * 入栈操作
     *
     * @param e
     */
    void push(E e);

    /**
     * 出栈操作
     *
     * @return
     */
    E pop();
}
