package com.zhuge.learn.dsaa.datastructure.tree;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Utils {

    public static <E extends Comparable<E>> BinaryFindTree<E> buildFindTree(Collection<E> elements) {
        if (null == elements || elements.isEmpty()) {
            return null;
        }
        Iterator<E> iterator = elements.iterator();
        E first = iterator.next();
        BinaryFindTree<E> tree = new BinaryFindTree<E>(first);
        while (iterator.hasNext()) {
            tree.insert(iterator.next());
        }
        return tree;
    }

    public static <E extends Comparable<E>> AVLTree<E> buildAVLTree(Collection<E> elements) {
        if (null == elements || elements.isEmpty()) {
            return null;
        }
        Iterator<E> iterator = elements.iterator();
        E first = iterator.next();
        AVLTree<E> tree = new AVLTree<E>(first);
        while (iterator.hasNext()) {
            tree.insert(iterator.next());
        }
        return tree;
    }

    public static <E extends Comparable<E>> BinaryFindTree<E> buildFindTree(E[] elements) {
        if (null == elements || elements.length < 1) {
            return null;
        }
        return buildFindTree(Arrays.asList(elements));
    }

    public static <E extends Comparable<E>> AVLTree<E> buildAVLTree(E[] elements) {
        if (null == elements || elements.length < 1) {
            return null;
        }
        return buildAVLTree(Arrays.asList(elements));
    }
}
