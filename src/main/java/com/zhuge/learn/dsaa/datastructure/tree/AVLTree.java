package com.zhuge.learn.dsaa.datastructure.tree;

public class AVLTree<E extends Comparable<E>> extends BinaryTree<E, AVLTree<E>> {

    private static final int ALLOWED_HEIGHT_DELTA = 1;

    public AVLTree(E e) {
        super(e);
    }

    public AVLTree(E e, AVLTree<E> leftChild, AVLTree<E> rightChild) {
        super(e, leftChild, rightChild);
    }

    /**
     * 插入方法需要考虑平衡
     *
     * @param e
     */
    public void insert(E e) {
        //不允许插入空值
        if (null == e) {
            return;
        }
        int compare = e.compareTo(element);
        if (compare < 0) {
            if (null == leftChild) {
                leftChild = new AVLTree<E>(e);
                refreshHeight();
            } else {
                leftChild.insert(e);
                rebalance();
            }
        } else if (compare > 0) {
            if (null == rightChild) {
                rightChild = new AVLTree<E>(e);
                refreshHeight();
            } else {
                rightChild.insert(e);
                rebalance();
            }
        }
        //相等，do nothing
    }

    /**
     * 重新平衡左右子树
     */
    private void rebalance() {
        int leftHeight = height(leftChild);
        int rightHeight = height(rightChild);
        //左子树比右子树高
        if (leftHeight - rightHeight > ALLOWED_HEIGHT_DELTA) {
            //向右单旋转
            if (height(leftChild.leftChild) > height(leftChild.rightChild)) {
                rotateRight();
            } else {
                doubleRotateRight();
            }
        }
        //右子树比左子树高
        else if (rightHeight - leftHeight > ALLOWED_HEIGHT_DELTA) {
            if (height(rightChild.rightChild) > height(rightChild.leftChild)) {
                rotateLeft();
            } else {
                doubleRotateLeft();
            }
        } else {
            this.height = Math.max(leftHeight, rightHeight) + 1;
        }
    }

    /**
     * 向右单旋转
     */
    private void rotateRight() {
        AVLTree<E> newRoot = leftChild.clone();
        AVLTree<E> oldRoot = this.clone();
        oldRoot.leftChild = newRoot.rightChild;
        newRoot.rightChild = oldRoot;
        //调整完成后更新高度
        oldRoot.refreshHeight();
        newRoot.refreshHeight();
        convertTo(newRoot);
    }

    /**
     * 向左单旋转
     */
    private void rotateLeft() {
        AVLTree<E> newRoot = rightChild.clone();
        AVLTree<E> oldRoot = this.clone();
        oldRoot.rightChild = newRoot.leftChild;
        newRoot.leftChild = oldRoot;
        //调整完成后更新高度
        oldRoot.refreshHeight();
        newRoot.refreshHeight();
        convertTo(newRoot);
    }

    /**
     * 左子树比右子树高，进行双旋转
     */
    private void doubleRotateRight() {
        //先对左子树进行向左旋转
        leftChild.rotateLeft();
        //在进行向右旋转
        rotateRight();
    }

    /**
     * 右子树比左子树高，进行双旋转
     */
    private void doubleRotateLeft() {
        //先对左子树进行向左旋转
        rightChild.rotateRight();
        //在进行向右旋转
        rotateLeft();
    }

    private void convertTo(AVLTree<E> tree) {
        this.rightChild = tree.rightChild;
        this.leftChild = tree.leftChild;
        this.element = tree.element;
        this.height = tree.height;
    }

    public AVLTree<E> find(E e) {
        if (null == e) {
            return null;
        }
        int compare = e.compareTo(element);
        if (compare < 0) {
            if (null == leftChild) {
                return null;
            } else {
                return leftChild.find(e);
            }
        } else if (compare > 0) {
            if (null == rightChild) {
                return null;
            } else {
                return rightChild.find(e);
            }
        } else {
            return this;
        }
    }
}
