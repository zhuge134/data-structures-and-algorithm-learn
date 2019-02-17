package com.zhuge.learn.dsaa.datastructure.tree;

import java.util.ArrayList;
import java.util.List;
/**
 * @Title: BinaryTree
 * @Description:
 * @author: zhuge
 * @date: 2019/2/17 23:14
 */
public abstract class BinaryTree<E,SELF extends BinaryTree<E,SELF>> {

    /**
     * 节点的元素
     */
    protected E element;

    /**
     * 左孩子
     */
    protected SELF leftChild;

    /**
     * 右孩子
     */
    protected SELF rightChild;

    public BinaryTree(E e, SELF leftChild, SELF rightChild) {
        this.element = e;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public BinaryTree(E e) {
        this.element = e;
    }

    public BinaryTree() {
    }

    public boolean isLeef() {
        return null == leftChild && null == rightChild;
    }

    /**
     * 中序遍历
     *
     * @return
     */
    public List<E> mediaTraversal() {
        List<E> list = new ArrayList<E>();
        if (null != leftChild) {
            list.addAll(leftChild.mediaTraversal());
        }
        list.add(element);
        if (null != rightChild) {
            list.addAll(rightChild.mediaTraversal());
        }
        return list;
    }

    /**
     * 前序遍历
     *
     * @return
     */
    public List<E> preTraversal() {
        List<E> list = new ArrayList<E>();
        list.add(element);
        if (null != leftChild) {
            list.addAll(leftChild.preTraversal());
        }
        if (null != rightChild) {
            list.addAll(rightChild.preTraversal());
        }
        return list;
    }

    /**
     * 后序遍历
     *
     * @return
     */
    public List<E> postTraversal() {
        List<E> list = new ArrayList<E>();
        if (null != leftChild) {
            list.addAll(leftChild.postTraversal());
        }
        if (null != rightChild) {
            list.addAll(rightChild.postTraversal());
        }
        list.add(element);
        return list;
    }
}
