package com.zhuge.learn.dsaa.datastructure.tree;

/**
 * @Title: BTree
 * @Description:
 * @author: zhuge
 * @date: 2019/2/22 19:31
 * TODO
 */
public class BTree<E extends Comparable<E>> {
    /**
     * 默认分叉树10
     */
    private static final int DEFAULT_M = 10;
    private static final int DEFAULT_L = 10;
    /**
     * 树的分叉数
     */
    private int m = DEFAULT_M;

    /**
     * 树的高度
     */
    private int height = 0;

    /**
     * 各子树的上界,有序数组，递增
     */
    private Object[] ranges = new Object[m - 1];

    /**
     * 界的个数
     */
    private int rangeNum = 0;

    /**
     * 子树
     */
    private Object[] children = new Object[m];

    /**
     * 子节点个数
     */
    private int childNum;

    /**
     * 是否叶子节点
     */
    private boolean isLeef;

    /**
     * 叶子节点的元素
     */
    private Object[] elements;

    /**
     * 叶子节点元素个数
     */
    private int elementNum;

    /**
     * 父节点引用
     */
    private BTree<E> parent;


    public E find(E e) {
        if (null == e) {
            return null;
        }
        int index = findRangeIndex(e);
        if (index < 0) {
            return null;
        }
        if (null == children[index]) {
            return null;
        }
        // TODO
        return null;
    }

    /**
     * 定位给定元素的界
     *
     * @param e
     * @return 如果没有元素则为-1,
     */
    @SuppressWarnings("unchecked")
    private int findRangeIndex(E e) {
        if (null == ranges || ranges.length < 1) {
            return -1;
        }
        int i = 0;
        for (; i < m - 1; i++) {
            if (null == ranges[i]) {
                break;
            }
            if (e.compareTo((E) ranges[i]) <= 0) {
                break;
            }
        }
        return i;
    }
}
