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
        elements[0] = elements[size - 1];
        elements[--size] = null;
        percolateDown(0);
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
            elements[0] = e;
            percolateDown(0);
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
        elements[size++] = e;
        percolateUp(size - 1);
        return true;
    }

    /**
     * 初始构建堆
     */
    private void buildHeap() {
        //从最后一个节点的父节点开始向前循环
        for (int i = parent(size - 1); i > -1; i--) {
            percolateDown(i);
        }
    }

    /**
     * 从该节点开始向下调整
     *
     * @param parent
     */
    @SuppressWarnings("unchecked")
    private void percolateDown(int parent) {
        int leftIndex = leftChild(parent);
        int rightIndex = rightChild(parent);
        E curNode = (E) elements[parent];
        if (leftIndex < size && rightIndex < size) {
            E left = (E) elements[leftIndex];
            E right = (E) elements[rightIndex];
            if (left.compareTo(right) <= 0) {
                if (curNode.compareTo(left) <= 0) {
                    // 当前节点是最小值，不需要调整
                    return;
                } else {
                    //左孩子是最小值，交换当前节点和左孩子，并调整左孩子
                    elements[parent] = left;
                    elements[leftIndex] = curNode;
                    percolateDown(leftIndex);

                }
            } else {
                if (curNode.compareTo(right) <= 0) {
                    // 当前节点是最小值，不需要调整
                    return;
                } else {
                    //右孩子是最小值，交换当前节点和右孩子，并调整右孩子
                    elements[parent] = right;
                    elements[rightIndex] = curNode;
                    percolateDown(rightIndex);
                }
            }
        } else if (leftIndex < size) {
            E left = (E) elements[leftIndex];
            if (left.compareTo(curNode) < 0) {
                //交换左孩子和当前节点
                elements[leftIndex] = curNode;
                elements[parent] = left;
                //无需调整，因为左孩子是最后一个节点
            }
        } else {
            //无需调整
        }
    }

    /**
     * 从该节点开始向上调整
     *
     * @param child
     */
    @SuppressWarnings("unchecked")
    private void percolateUp(int child) {
        E curNode = (E) elements[child];
        int parentIndex = parent(child);
        if (parentIndex > -1) {
            E parent = (E) elements[parentIndex];
            if (curNode.compareTo(parent) < 0) {
                elements[child] = parent;
                elements[parentIndex] = curNode;
                percolateUp(parentIndex);
            } else {
                //当前节点比父节点大，不需要调整
                return;
            }
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
