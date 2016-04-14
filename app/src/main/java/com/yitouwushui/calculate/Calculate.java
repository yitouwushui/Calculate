package com.yitouwushui.calculate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Created by yitouwushui on 2015/11/10.
 */
public class Calculate {

    /**
     * 利用Stack计算四则运算字符串表达式的值，如果抛出异常，则说明表达式有误
     *
     * @param computeText 四则运算字符串表达式
     * @return double 类型的结果
     */
    public BigDecimal computeStack(String computeText) {

        StringTokenizer tokenizer = new StringTokenizer(computeText, "+-*/^√()", true);
        /**
         * 数字栈
         */
        Stack<BigDecimal> numStack = new Stack<>();
        /**
         * 运算符栈
         */
        Stack<Operator> operStack = new Stack<>();
        Map<String, Operator> computeOper = this.getComputeOper();
        String currentEle;
        Operator curOper;
        while (tokenizer.hasMoreTokens()) {
            currentEle = tokenizer.nextToken().trim();
            //如果不为空
            if (!"".equals(currentEle)) {

                if (this.isNum(currentEle)) {
                    numStack.push(BigDecimal.valueOf(Double.valueOf(currentEle)));
                } else {
//                    // 获取对应的运算符
//                    curOper = computeOper.get(currentEle);
//                    if (curOper != null) {
//                        // 是运算符
//                        while (!operStack.empty()
//                                && operStack.peek().priority()
//                                >= curOper.priority()) {
//                            compute(numStack, operStack);
//                        }
//
//                        // 计算完成以后，把当前字符放到栈中
//                        operStack.push(curOper);
//                    } else {
//                        // 是括号
//                        if ("(".equals(currentEle)) {
//                            operStack.push(curOper);
//                        } else {
//                            // 将括号内的全部计算
//                            while (!operStack.peek().equals(Operator.BRACKETS)) {
//                                compute(numStack, operStack);
//                            }
//
//                            // 移除栈顶的左括号
//                            operStack.pop();
//                        }
//                    }
                    curOper = computeOper.get(currentEle);
                    switch (currentEle) {
                        case "+":
                        case "-":
                        case "*":
                        case "/":
                        case "^":
                        case "√":
                            while (!operStack.empty()
                                    && operStack.peek().priority()
                                    >= curOper.priority()) {
                                compute(numStack, operStack);
                            }

                            // 计算完成以后，把当前字符放到栈中
                            operStack.push(curOper);
                            break;
                        case "(":
                            operStack.push(curOper);
                            break;
                        case ")":
                            // 将括号内的全部计算
                            while (!operStack.peek().equals(Operator.BRACKETS)) {
                                compute(numStack, operStack);
                            }
                            // 移除栈顶的左括号
                            operStack.pop();
                            break;
                    }

                }
            }
        }
        // 经过上面代码的遍历后最后的应该是nums里面剩两个数或三个数，operators里面剩一个或两个运算操作符
        while (!operStack.empty()) {
            compute(numStack, operStack);
        }
        return numStack.pop();
    }


    /**
     * 判断一个字符串是否数字类型
     *
     * @param str
     * @return
     */
    private boolean isNum(String str) {
        String numRegex = "^\\d+(\\.\\d+)?$";
        return Pattern.matches(numRegex, str);
    }

    /**
     * 获取运算操作符
     *
     * @return
     */
    public Map<String, Operator> getComputeOper() {
        Map<String, Operator> hm = new HashMap<String, Operator>();
        hm.put("+", Operator.PLUS);
        hm.put("-", Operator.MINUS);
        hm.put("*", Operator.MULTIPLY);
        hm.put("/", Operator.DIVIDE);
        hm.put("^", Operator.EXPONENT);
        hm.put("√", Operator.RADICAL);
        hm.put("(", Operator.BRACKETS);
        hm.put(")", Operator.BRACKETS);

        return hm;
    }

    private void compute(Stack<BigDecimal> numStack, Stack<Operator> operStack) {
        // 按顺序弹出数字栈最顶端两个数字进行运算。
        BigDecimal num2 = numStack.pop();
        BigDecimal num1 = numStack.pop();

        // 弹出字符栈最顶端的字符串进行运算
        BigDecimal computeResult = operStack.pop().compute(num1, num2);
        // 把运算结果重新放到数字栈顶
        numStack.push(computeResult);
    }
}
