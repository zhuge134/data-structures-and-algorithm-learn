package com.zhuge.learn.dsaa.datastructure.stack;

public class Utils {
    /**
     * 中缀表达式转后缀表达式
     *
     * @param mediafix
     * @return
     */
    public static String media2Post(String mediafix) {
        //Stack<Character> characterStack = new ArrayStack<Character>(256);
        Stack<Character> characterStack = new ListStack<Character>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mediafix.length(); i++) {
            char c = mediafix.charAt(i);
            if (isOperate(c)) {
                //将栈内所有比当前操作符优先级高的操作符输出，然后将当前操作符入栈
                for (; ; ) {
                    Character top = characterStack.pop();
                    if (null == top) {
                        characterStack.push(c);
                        break;
                    } else if (isLeftBracket(top)) {
                        characterStack.push(top);
                        characterStack.push(c);
                        break;
                    } else {
                        int order = compareOperate(c, top);
                        if (order < 0) {
                            sb.append(top);
                        } else {
                            characterStack.push(top);
                            characterStack.push(c);
                            break;
                        }
                    }
                }
            } else if (isLeftBracket(c)) {
                //直接放入栈顶
                characterStack.push(c);
            } else if (isRightBracket(c)) {
                //将栈中的元素依次输出，直到遇到第一个左括号
                for (; ; ) {
                    Character top = characterStack.pop();
                    if (null == top) {
                        throw new RuntimeException("something wrong!");
                    }
                    if (isLeftBracket(top)) {
                        break;
                    } else {
                        sb.append(top);
                    }
                }
            } else {
                sb.append(c);
            }
        }
        //最后将栈内的操作符依次输出
        for (; ; ) {
            Character top = characterStack.pop();
            if (null == top) {
                break;
            }
            sb.append(top);
        }
        return sb.toString();
    }

    /**
     * 暂时只支持加减乘除
     *
     * @param c
     * @return
     */
    public static boolean isOperate(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static final int[][] operateOrder = new int[][]{
            {0, 0, -1, -1},
            {0, 0, -1, -1},
            {1, 1, 0, 0},
            {1, 1, 0, 0}};

    private static char[] operates = {'+', '-', '*', '/'};

    private static int indexOfOperate(char c) {
        for (int i = 0; i < operates.length; i++) {
            if (c == operates[i]) {
                return i;
            }
        }
        return -1;
    }

    public static int compareOperate(char a, char b) {
        int index_a = indexOfOperate(a);
        int index_b = indexOfOperate(b);
        return operateOrder[index_a][index_b];
    }

    public static boolean isLeftBracket(char c) {
        return c == '(';
    }

    public static boolean isRightBracket(char c) {
        return c == ')';
    }
}
