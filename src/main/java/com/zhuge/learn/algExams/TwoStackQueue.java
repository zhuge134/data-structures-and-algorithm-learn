package com.zhuge.learn.algExams;

import java.util.Stack;

/**
 * @Title: TwoStackQueue
 * @Description:
 * @author: zhuge
 * @date: 2019/3/17 22:41
 */
public class TwoStackQueue<E> {
    public static void main(String[] args) {
        TwoStackQueue<Integer> queue = new TwoStackQueue<>();
        for (int i = 0; i < 50; i++) {
            queue.offer(i);
        }
        for (int i = 0; i < 25; i++) {
            System.out.print(" " + queue.poll());
        }

        for (int i = 50; i < 75; i++) {
            queue.offer(i);
        }
        while (true) {
            Integer e = queue.poll();
            if (null == e) {
                break;
            }
            System.out.print(" " + e);
        }
    }

    /**
     * 往里放元素，对应队列的offer
     */
    private Stack<E> in = new Stack<>();
    /**
     *
     */
    private Object inLock = new Object();
    /**
     * 往外拿元素, 对应队列的poll
     */
    private Stack<E> out = new Stack<>();
    private Object outLock = new Object();

    public void offer(E e) {
        synchronized (inLock) {
            in.push(e);
        }
    }

    public E poll() {
        synchronized (outLock) {
            if (out.isEmpty()) {
                //此时需要将in中的元素全部转移到out中
                synchronized (inLock) {
                    while (!in.isEmpty()) {
                        out.push(in.pop());
                    }
                }
            }
            if (!out.isEmpty()) {
                return out.pop();
            } else {
                return null;
            }
        }
    }
}
