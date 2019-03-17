package com.zhuge.learn.algExams;

public interface IListNode<E> {
    IListNode<E> prev();

    IListNode<E> next();

    E e();

}
