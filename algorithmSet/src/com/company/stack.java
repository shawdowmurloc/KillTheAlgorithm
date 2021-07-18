package com.company;

import java.util.Stack;

/*******
 * 栈相关问题
 * 1.lc20 有效的括号
 *
 */
     /////////////
    //1.有效的括号
    //////////////
    //栈先入后出特点恰好与本题括号排序特点一致，即若遇到左括号入栈，
    //遇到右括号时将对应栈顶左括号出栈，则遍历完所有括号后 stack 仍然为空；
    public class stack {
        public boolean isValid(String s) {
            if(s.isEmpty()){
                return true;
            }
            Stack<Character> stack=new Stack<Character>();
            for(char c:s.toCharArray()){
                if(c=='(')
                    stack.push(')');
                else if(c=='{')
                    stack.push('}');
                else if(c=='[')
                    stack.push(']');
                else if(stack.empty()||c!=stack.pop())
                    return false;
            }
            if(stack.empty())
                return true;
            return false;

        }

}
