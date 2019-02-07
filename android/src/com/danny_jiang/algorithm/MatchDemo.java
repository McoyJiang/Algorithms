package com.danny_jiang.algorithm;

import java.util.ArrayList;
import java.util.Stack;

public class MatchDemo {
    static ArrayList<Character> charMatch = new ArrayList<Character>(){{
        add('(');
        add('[');
        add('{');
    }};

    public static void main(String[] args) {
        String testString = "(]";
        boolean ans = check(testString);
        System.out.println(ans);
    }

    /**
     * 判断传入的字符串是否是合法的括号匹配
     *
     * @param testString
     * @return
     *      "(([{}]))"  返回true
     *      "(]"        返回false
     */
    public static boolean check(String testString) {
        Stack<Character> s = new Stack<>();
        for (int i = 0; i < testString.length(); i++) {
            char charAt = testString.charAt(i);
            // 如果charAt是 '(' 或 '[' 或 '{'，则将其压入到栈顶
            if (charMatch.contains(charAt)) {
                s.push(charAt);
            }
            else if ((s.peek() + String.valueOf(charAt)).equals("()")
                    || (s.peek() + String.valueOf(charAt)).equals("[]")
                    || (s.peek() + String.valueOf(charAt)).equals("{}")) {
                s.pop();
            }
        }
        return s.isEmpty();   //如果最后辅助栈的元素全部弹出则为符合规范
    }
}
