package com.zhuge.learn.dsaa.datastructure.heap;

/**
 * @Title: BinaryHeap
 * @Description: 二叉堆的数组实现，有序对，小顶堆
 * @author: zhuge
 * @date: 2019/2/22 20:27
 */
public class BinaryHeap<E extends Comparable<E>> {
    /**
     * 存放元素的数组
     */
    private Object[] elements;

    /**
     * 堆的大小
     */
    private int size;

    /**
     * 堆的最大容量
     */
    private int capacity;

    public BinaryHeap(E[] elements) {
        this.elements = elements;
        this.size = elements.length;
        this.capacity = size;
        buildHeap();
    }

    public BinaryHeap(E[] elements, int capacity) {
        if (capacity < elements.length) {
            throw new IllegalArgumentException();
        }
        this.elements = new Object[capacity];
        System.arraycopy(elements, 0, this.elements, 0, elements.length);
        this.size = elements.length;
        this.capacity = capacity;
        buildHeap();
    }

    public BinaryHeap(int capacity) {
        this.capacity = capacity;
        this.elements = new Object[capacity];
        this.size = 0;
    }


    /**
     * 删除堆顶
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public E deleteMin() {
        if (size < 1) {
            return null;
        }
        E top = (E) elements[0];
        E last = (E) elements[size - 1];
        elements[--size] = null;
        percolateDown(0, last);
        return top;
    }

    /**
     * 如果比顶点大，则删除顶点并插入
     *
     * @return
     */
    public boolean compareAndInsert(E e) {
        if (null == e) {
            return false;
        }
        if (size < 1) {
            elements[0] = e;
        }
        if (e.compareTo((E) elements[0]) > 0) {
            percolateDown(0, e);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 插入
     *
     * @param e
     * @return
     */
    public boolean insert(E e) {
        if (null == e) {
            return false;
        }
        if (size == capacity) {
            return false;
        }
        size++;
        percolateUp(size - 1, e);
        return true;
    }

    /**
     * 初始构建堆
     */
    @SuppressWarnings("unchecked")
    private void buildHeap() {
        //从最后一个节点的父节点开始向前循环
        for (int i = parent(size - 1); i > -1; i--) {
            percolateDown(i, (E) elements[i]);
        }
    }

    /**
     * 从该节点开始向下调整
     *
     * @param curIndex
     */
    @SuppressWarnings("unchecked")
    private void percolateDown(int curIndex, E node) {
        if (null == node) {
            throw new NullPointerException();
        }
        int leftIndex = leftChild(curIndex);
        int rightIndex = rightChild(curIndex);
        if (leftIndex < size && rightIndex < size) {
            E left = (E) elements[leftIndex];
            E right = (E) elements[rightIndex];
            if (left.compareTo(right) <= 0) {
                if (node.compareTo(left) <= 0) {
                    // 当前节点是最小值，不需要调整
                    elements[curIndex] = node;
                } else {
                    //左孩子是最小值，交换当前节点和左孩子，并调整左孩子
                    elements[curIndex] = left;
                    percolateDown(leftIndex, node);
                }
            } else {
                if (node.compareTo(right) <= 0) {
                    // 当前节点是最小值，不需要调整
                    elements[curIndex] = node;
                } else {
                    //右孩子是最小值，交换当前节点和右孩子，并调整右孩子
                    elements[curIndex] = right;
                    percolateDown(rightIndex, node);
                }
            }
        } else if (leftIndex < size) {
            E left = (E) elements[leftIndex];
            if (left.compareTo(node) < 0) {
                //交换左孩子和当前节点
                elements[leftIndex] = node;
                elements[curIndex] = left;
                //无需调整，因为左孩子是最后一个节点
            }
        } else {
            //其余情况无需调整
            elements[curIndex] = node;
        }
    }

    /**
     * 从该节点开始向上调整
     *
     * @param curIndex
     */
    @SuppressWarnings("unchecked")
    private void percolateUp(int curIndex, E node) {
        int parentIndex = parent(curIndex);
        if (parentIndex > -1) {
            E parent = (E) elements[parentIndex];
            if (node.compareTo(parent) < 0) {
                elements[curIndex] = parent;
                percolateUp(parentIndex, node);
            } else {
                //当前节点比父节点大，不需要调整
                elements[curIndex] = node;
            }
        } else {
            elements[curIndex] = node;
        }
    }

    /**
     * 计算父节点下表
     *
     * @param child
     * @return
     */
    private int parent(int child) {
        return (child + 1) / 2 - 1;
    }

    /**
     * 计算左孩子的下标
     *
     * @param parent
     * @return
     */
    private int leftChild(int parent) {
        return 2 * parent + 1;
    }

    /**
     * 计算右孩子下标
     *
     * @param parent
     * @return
     */
    private int rightChild(int parent) {
        return 2 * parent + 2;
    }

}
