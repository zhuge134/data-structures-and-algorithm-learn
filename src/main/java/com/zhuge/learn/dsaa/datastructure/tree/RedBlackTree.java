package com.zhuge.learn.dsaa.datastructure.tree;

import java.util.function.Consumer;

/**
 * @Title: RedBlackTree
 * @Description: 非线程安全
 * @author: zhuge
 * @date: 2019/3/1 22:03
 */
public class RedBlackTree<E extends Comparable<? super E>> {

    private static final int RED_COLOR = 1;
    private static final int BLACK_COLOR = 0;

    private TreeNode<E> root;

    private long adjust_count = 0;

    public RedBlackTree() {
    }

    public boolean find(E e) {
        return find(e, root);
    }

    /**
     * 在一棵树中查找元素
     *
     * @param e
     * @param tree
     * @return
     */
    private boolean find(E e, TreeNode<E> tree) {
        if (null == e || null == tree) {
            return false;
        }
        int compare = e.compareTo(tree.element);
        if (0 == compare) {
            return true;
        } else if (compare < 0) {
            if (null != tree.left) {
                return find(e, tree.left);
            } else {
                return false;
            }
        } else {
            if (null != tree.right) {
                return find(e, tree.right);
            } else {
                return false;
            }
        }
    }

    /**
     * 插入方法
     *
     * @param e
     */
    public void insert(E e) {
        if (null == root) {
            root = new TreeNode<>(e, null, null, 0, null);
            return;
        }
        insert(e, root);
    }

    /**
     * 插入
     *
     * @param e
     * @param tree
     */
    private void insert(E e, TreeNode<E> tree) {
        int compare = e.compareTo(tree.element);
        if (compare == 0) {
            //重复元素，不做任何处理
            return;
        } else if (compare < 0) {
            if (null != tree.left) {
                insert(e, tree.left);
            } else {
                //左孩子为空的情况
                //首先构造一个红节点
                TreeNode<E> newNode = new TreeNode<>(e, null, null, RED_COLOR, tree);
                //将左孩子指向新节点
                tree.left = newNode;
                //如果该节点是红色节点, 则需要调整
                if (isRed(tree)) {
                    adjust(newNode);
                }
            }
        } else {
            if (null != tree.right) {
                insert(e, tree.right);
            } else {
                //右孩子为空的情况
                //首先构造一个红节点
                TreeNode<E> newNode = new TreeNode<>(e, null, null, RED_COLOR, tree);
                tree.right = newNode;
                if (isRed(tree)) {
                    adjust(newNode);
                }
            }
        }
    }

    /**
     * 调整颜色，tree节点与tree的父节点都是红色
     *
     * @param c
     */
    private void adjust(TreeNode<E> c) {
        //父节点
        TreeNode<E> p = c.parent;
        //祖父节点
        TreeNode<E> g = p.parent;

        //如果父节点是根节点，那么直接将根节点颜色设置为黑色即可
        if (isRoot(p)) {
            p.color = BLACK_COLOR;
            return;
        }

        //祖父节点的父节点
        TreeNode<E> gf = g.parent;

        //叔父节点是黑色，向右单旋转
        if (g.left == p && p.left == c && !isRed(g.right)) {
            TreeNode<E> c3 = p.right;
            p.parent = gf;
            p.right = g;
            g.parent = p;
            g.left = c3;
            if (null != c3) {
                c3.parent = g;
            }
            //调整颜色
            p.color = BLACK_COLOR;
            g.color = RED_COLOR;
            if (isRoot(p)) {
                root = p;
                root.color=BLACK_COLOR;
            } else {
                if (gf.left == g) {
                    gf.left = p;
                } else {
                    gf.right = p;
                }
            }
            return;
        }
        //叔父节点是黑色，向右双旋转
        if (g.left == p && p.right == c && !isRed(g.right)) {
            TreeNode<E> c1 = c.left;
            TreeNode<E> c2 = c.right;
            p.right = c1;
            p.parent = c;
            c.parent = gf;
            c.left = p;
            c.right = g;
            g.parent = c;
            g.left = c2;
            if (null != c1) {
                c1.parent = p;
            }
            if (null != c2) {
                c2.parent = g;
            }
            //调整颜色
            c.color = BLACK_COLOR;
            g.color = RED_COLOR;
            if (isRoot(c)) {
                root = c;
                root.color=BLACK_COLOR;
            }else {
                if (gf.left == g) {
                    gf.left = c;
                } else {
                    gf.right = c;
                }
            }
            return;
        }
        //叔父节点是红色，向右单旋转
        if (g.left == p && p.left == c && isRed(g.right)) {
            TreeNode<E> c3 = p.right;
            p.parent = gf;
            p.right = g;
            g.parent = p;
            g.left = c3;
            if (null != c3) {
                c3.parent = g;
            }
            //调整颜色
            c.color = BLACK_COLOR;
            if (isRoot(p)) {
                root = p;
                root.color=BLACK_COLOR;
            }else {
                if (gf.left == g) {
                    gf.left = p;
                } else {
                    gf.right = p;
                }
            }
            if (isRed(gf)) {
                adjust(p);
            }
            return;
        }
        //叔父节点是红色，向右双旋转
        if (g.left == p && p.right == c && isRed(g.right)) {
            TreeNode<E> c1 = c.left;
            TreeNode<E> c2 = c.right;
            p.right = c1;
            p.parent = c;
            c.parent = gf;
            c.left = p;
            c.right = g;
            g.parent = c;
            g.left = c2;
            if (null != c1) {
                c1.parent = p;
            }
            if (null != c2) {
                c2.parent = g;
            }
            //调整颜色
            p.color = BLACK_COLOR;
            if (isRoot(c)) {
                root = c;
                root.color=BLACK_COLOR;
            }else {
                if (gf.left == g) {
                    gf.left = c;
                } else {
                    gf.right = c;
                }
            }
            if (isRed(gf)) {
                adjust(c);
            }
            return;
        }

        //叔父节点是黑色，向左单旋转
        if (g.right == p && p.right == c && !isRed(g.left)) {
            TreeNode<E> c3 = p.left;
            p.parent = gf;
            p.left = g;
            g.parent = p;
            g.right = c3;
            if (null != c3) {
                c3.parent = g;
            }
            //调整颜色
            p.color = BLACK_COLOR;
            g.color = RED_COLOR;
            if (isRoot(p)) {
                root = p;
                root.color=BLACK_COLOR;
            }else {
                if (gf.left == g) {
                    gf.left = p;
                } else {
                    gf.right = p;
                }
            }
            return;
        }
        //叔父节点是黑色，向左双旋转
        if (g.right == p && p.left == c && !isRed(g.left)) {
            TreeNode<E> c1 = c.right;
            TreeNode<E> c2 = c.left;
            p.left = c1;
            p.parent = c;
            c.parent = gf;
            c.right = p;
            c.left = g;
            g.parent = c;
            g.right = c2;
            if (null != c1) {
                c1.parent = p;
            }
            if (null != c2) {
                c2.parent = g;
            }
            //调整颜色
            c.color = BLACK_COLOR;
            g.color = RED_COLOR;
            if (isRoot(c)) {
                root = c;
                root.color=BLACK_COLOR;
            }else {
                if (gf.left == g) {
                    gf.left = c;
                } else {
                    gf.right = c;
                }
            }
            return;
        }
        //叔父节点是红色，向左单旋转
        if (g.right == p && p.right == c && isRed(g.left)) {
            TreeNode<E> c3 = p.left;
            p.parent = gf;
            p.left = g;
            g.parent = p;
            g.right = c3;
            if (null != c3) {
                c3.parent = g;
            }
            //调整颜色
            c.color = BLACK_COLOR;
            if (isRoot(p)) {
                root = p;
                root.color=BLACK_COLOR;
            }else {
                if (gf.left == g) {
                    gf.left = p;
                } else {
                    gf.right = p;
                }
            }
            if (isRed(gf)) {
                adjust(p);
            }
            return;
        }
        //叔父节点是红色，向左双旋转
        if (g.right == p && p.left == c && isRed(g.left)) {
            TreeNode<E> c1 = c.right;
            TreeNode<E> c2 = c.left;
            p.left = c1;
            p.parent = c;
            c.parent = gf;
            c.right = p;
            c.left = g;
            g.parent = c;
            g.right = c2;
            if (null != c1) {
                c1.parent = p;
            }
            if (null != c2) {
                c2.parent = g;
            }
            //调整颜色
            p.color = BLACK_COLOR;
            if (isRoot(c)) {
                root = c;
                root.color=BLACK_COLOR;
            }else {
                if (gf.left == g) {
                    gf.left = c;
                } else {
                    gf.right = c;
                }
            }
            if (isRed(gf)) {
                adjust(c);
            }
            return;
        }
    }

    /**
     * 判断一个节点是否为红色节点
     *
     * @param tree
     * @return
     */
    private boolean isRed(TreeNode<E> tree) {
        //null节点约定是黑色
        if (null == tree) {
            return false;
        }
        return tree.color == RED_COLOR;
    }

    static class TreeNode<E extends Comparable<? super E>> {
        private E element;
        private TreeNode<E> left;
        private TreeNode<E> right;
        /**
         * 默认是0，即黑色
         */
        private int color;
        /**
         * 保存父节点的引用
         */
        private TreeNode<E> parent;

        public TreeNode(E element, TreeNode<E> left, TreeNode<E> right, int color, TreeNode<E> parent) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.color = color;
            this.parent = parent;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    /**
     * 判断一个节点是不是根
     *
     * @param e
     * @return
     */
    private boolean isRoot(TreeNode<E> e) {
        return e != null && null == e.parent;
    }

    /**
     * 中序遍历
     *
     * @param consumer
     */
    public void middleTraversal(Consumer<E> consumer) {
        middleTraversal(consumer, root);
    }

    /**
     * 中序遍历
     *
     * @param consumer
     * @param tree
     */
    private void middleTraversal(Consumer<E> consumer, TreeNode<E> tree) {
        if (null == tree) {
            return;
        }
        if (null != tree.left) {
            middleTraversal(consumer, tree.left);
        }
        consumer.accept(tree.element);
        if (null != tree.right) {
            middleTraversal(consumer, tree.right);
        }
    }
}
