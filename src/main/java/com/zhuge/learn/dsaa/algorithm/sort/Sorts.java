package com.zhuge.learn.dsaa.algorithm.sort;

import java.util.Comparator;

/**
 * @Title: Sorts
 * @Description:
 * @author: zhuge
 * @date: 2019/2/23 20:49
 */
public class Sorts {

    /**
     * 插入排序，时间复杂度O(N^2)
     *
     * @param arr
     * @param <E>
     */
    public static <E extends Comparable<E>> void insertSort(E[] arr, int start, int end, Comparator<E> comparator) {
        if (null == arr || arr.length < 1 || start < 0) {
            return;
        }
        if (null == comparator) {
            comparator = Comparator.naturalOrder();
        }
        if (end > arr.length) {
            end = arr.length;
        }
        for (int i = start + 1; i < end; i++) {
            E cur = arr[i];
            int j = i - 1;
            for (; j > start - 1; j--) {
                if (comparator.compare(cur, arr[j]) < 0) {
                    arr[j + 1] = arr[j];
                } else {
                    arr[j + 1] = cur;
                    break;
                }
            }
            if (j < start) {
                arr[start] = cur;
            }
        }
    }

    /**
     * 快速排序，时间复杂度O(NlogN)
     *
     * @param arr
     * @param <E>
     */
    public static <E extends Comparable<E>> void quickSort(E[] arr, int start, int end, Comparator<E> comparator) {
        if (null == arr || arr.length < 1 || start < 0) {
            return;
        }
        if (null == comparator) {
            comparator = Comparator.naturalOrder();
        }
        if (end > arr.length) {
            end = arr.length;
        }
        //如果要排序的元素个数小于10，那么采用插入排序，提高速度，同时避免边界条件的判断
        if (end - start < 10) {
            insertSort(arr, start, end, comparator);
            return;
        }

        //选取中间值,并将中间值放到倒数第二个，将较小值放到第一个，较大值放到最后一个
        int middle = (start + end) / 2;
        if (comparator.compare(arr[middle], arr[start]) < 0) {
            swap(arr, start, middle);
        }
        if (comparator.compare(arr[end - 1], arr[start]) < 0) {
            swap(arr, start, end - 1);
        }
        if (comparator.compare(arr[middle], arr[end - 1]) > 0) {
            swap(arr, middle, end - 1);
        }
        swap(arr, middle, end - 2);
        //从两边想中间遍历，
        int left = start + 1;
        int right = end - 3;
        while (true) {
            if (left > right) {
                swap(arr, left, end - 2);
                break;
            }
            if (comparator.compare(arr[left], arr[end - 2]) > 0 && comparator.compare(arr[right], arr[end - 2]) < 0) {
                swap(arr, left, right);
                left++;
                right--;
                continue;
            }
            //等于也继续向前走
            if (comparator.compare(arr[left], arr[end - 2]) <= 0) {
                left++;
            }
            if (comparator.compare(arr[right], arr[end - 2]) >= 0) {
                right--;
            }
        }
        quickSort(arr, start, left, comparator);
        quickSort(arr, left + 1, end, comparator);
    }

    public static <E> void swap(E[] arr, int i, int j) {
        E tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static <E extends Comparable<E>> void mergeSort(E[] arr, int start, int end, Comparator<E> comparator, E[] tmp) {
        if (null == arr || arr.length < 1 || start < 0 || tmp.length < arr.length) {
            return;
        }
        if (end > arr.length) {
            end = arr.length;
        }
        if (null == comparator) {
            comparator = Comparator.naturalOrder();
        }

        if (end - start < 3) {
            insertSort(arr, start, end, comparator);
            return;
        }

        int middle = (start + end) / 2;
        mergeSort(arr, start, middle, comparator, tmp);
        mergeSort(arr, middle, end, comparator, tmp);

        //merge
        int left = start;
        int right = middle;
        int tmpCurIndex = 0;
        while (true) {
            //左边没了，右边还有
            if (left > middle - 1 && right < end) {
                for (; right < end; right++) {
                    tmp[tmpCurIndex++] = arr[right];
                }
                break;
            }
            //左边还有，右边没了
            if (left < middle && right > end - 1) {
                for (; left < middle; left++) {
                    tmp[tmpCurIndex++] = arr[left];
                }
                break;
            }
            //左边右边都没了
            if (left > middle - 1 && right > end - 1) {
                break;
            }
            //左右边都有
            if (comparator.compare(arr[left], arr[right]) < 0) {
                tmp[tmpCurIndex++] = arr[left++];
            } else {
                tmp[tmpCurIndex++] = arr[right++];
            }
        }
        //将tmp拷贝到原数组中
        for (int i = start; i < end; i++) {
            arr[i] = tmp[i-start];
        }
    }

    /**
     * 堆排序，时间复杂度O(NlogN)
     *
     * @param arr
     * @param <E>
     */
    public static <E extends Comparable<E>> void heapSort(E[] arr, int start, int end, Comparator<E> comparator) {
        if (null == arr || arr.length < 1 || start < 0) {
            return;
        }
        if (null == comparator) {
            comparator = Comparator.reverseOrder();
        }
        if (end > arr.length) {
            end = arr.length;
        }
        HeapSort.getInstance().sort(arr, start, end, comparator);
    }

    static class HeapSort {

        public <E extends Comparable<E>> void sort(E[] arr, int start, int end, Comparator<E> comparator) {
            buildHeap(arr, start, end, comparator);
            while (end - start > 1) {
                E tmp = arr[--end];
                arr[end] = arr[start];
                percolateDown(arr, start, end, start, tmp, comparator);
            }
        }

        /**
         * 构建堆
         *
         * @param arr
         * @param start
         * @param end
         * @param <E>
         */
        public <E extends Comparable<E>> void buildHeap(E[] arr, int start, int end, Comparator<E> comparator) {
            if (null == arr || arr.length < 1 || start < 0) {
                return;
            }
            if (end > arr.length) {
                end = arr.length;
            }
            for (int i = parent(start, end, end - 1); i > start - 1; i--) {
                percolateDown(arr, start, end, i, arr[i], comparator);
            }
        }

        /**
         * 上滤
         *
         * @param arr
         * @param start
         * @param end
         * @param index
         * @param e
         * @param <E>
         */
        private <E extends Comparable<E>> void percolateUp(E[] arr, int start, int end, int index, E e, Comparator<E> comparator) {
            if (null == arr || arr.length < 1 || start < 0) {
                return;
            }

            int parentIndex = parent(start, end, index);
            if (parentIndex < start) {
                arr[index] = e;
                return;
            }
            if (comparator.compare(e, arr[parentIndex]) < 0) {
                arr[index] = arr[parentIndex];
                percolateUp(arr, start, end, parentIndex, e, comparator);
            } else {
                arr[index] = e;
            }
        }

        /**
         * 下滤
         *
         * @param arr
         * @param start
         * @param end
         * @param index
         * @param e
         * @param <E>
         */
        private <E extends Comparable<E>> void percolateDown(E[] arr, int start, int end, int index, E e, Comparator<E> comparator) {
            if (null == e || index < start || index >= end) {
                throw new IllegalArgumentException();
            }

            int leftIndex = leftChild(start, end, index);
            int rightIndex = leftIndex + 1;

            if (rightIndex < end) {
                if (comparator.compare(arr[leftIndex], arr[rightIndex]) < 0) { //左孩子较小
                    if (comparator.compare(e, arr[leftIndex]) > 0) {
                        arr[index] = arr[leftIndex];
                        percolateDown(arr, start, end, leftIndex, e, comparator);
                    } else {
                        arr[index] = e;
                    }
                } else { //右孩子较小
                    if (comparator.compare(e, arr[rightIndex]) > 0) {
                        arr[index] = arr[rightIndex];
                        percolateDown(arr, start, end, rightIndex, e, comparator);
                    } else {
                        arr[index] = e;
                    }

                }
            } else if (leftIndex < end) {
                if (comparator.compare(arr[leftIndex], e) < 0) {
                    arr[index] = arr[leftIndex];
                    arr[leftIndex] = e;
                } else {
                    arr[index] = e;
                }
            } else {
                arr[index] = e;
            }
        }

        /**
         * 计算父节点下标
         *
         * @param start
         * @param end
         * @param child
         * @return
         */
        private int parent(int start, int end, int child) {
            return (child - start + 1) / 2 - 1 + start;
        }

        /**
         * 计算左孩子下标
         *
         * @param start
         * @param end
         * @param parent
         * @return
         */
        private int leftChild(int start, int end, int parent) {
            return 2 * (parent - start + 1) - 1 + start;
        }

        private static HeapSort instance;
        private static final Object INSTANCE_LOCK = new Object();

        private HeapSort() {
        }

        public static HeapSort getInstance() {
            if (null == instance) {
                synchronized (INSTANCE_LOCK) {
                    if (null == instance) {
                        instance = new HeapSort();
                    }
                }
            }
            return instance;
        }
    }
}
