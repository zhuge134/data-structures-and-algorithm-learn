package com.zhuge.learn.dsaa.datastructure.tree;

public class BinaryFindTree<E extends Comparable<E>>
        extends BinaryTree<E, BinaryFindTree<E>> {

    /**
     * 当前节点相等的元素个数
     */
    private int elementNum = 0;

    public BinaryFindTree(E e) {
        super(e);
        elementNum++;
    }

    public BinaryFindTree(E e, BinaryFindTree<E> leftChild, BinaryFindTree<E> rightChild) {
        super(e, leftChild, rightChild);
        elementNum++;
    }

    /**
     * 插入
     *
     * @param e
     */
    public void insert(E e) {
        if (null == e) {
            return;
        }
        int compare = e.compareTo(element);
        if (compare == 0) {
            elementNum++;
        } else if (compare < 0) {
            //插入左子树中，如果左子树为空，新建一个节点作为左子树
            if (null == leftChild) {
                leftChild = new BinaryFindTree<E>(e);
            } else {
                leftChild.insert(e);
            }
        } else {
            //插入右子树中，如果右子树为空，新建一个节点作为右子树
            if (null == rightChild) {
                rightChild = new BinaryFindTree<E>(e);
            } else {
                rightChild.insert(e);
            }
        }
    }

    public BinaryFindTree<E> find(E e) {
        if (null == e) {
            return null;
        }
        int compare = e.compareTo(element);
        if (compare == 0) {
            return this;
        } else if (compare < 0) {
            //在左子树中查找
            if (null == leftChild) {
                return null;
            } else {
                return leftChild.find(e);
            }
        } else {
            //在右子树中查找
            if (null == rightChild) {
                return null;
            } else {
                return rightChild.find(e);
            }
        }
    }
}
