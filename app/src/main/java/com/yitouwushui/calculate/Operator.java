package com.yitouwushui.calculate;

import java.math.BigDecimal;

/**
 * 运算符
 * Created by yitouwushui on 2015/11/12.
 */
public enum Operator {

    /**
     * 加
     */
    PLUS {
        @Override
        public int priority() {
            return 1;
        }

        @Override
        public BigDecimal compute(BigDecimal num1, BigDecimal num2) {
            return num1.add(num2);
        }
    },

    /**
     * 减
     */
    MINUS {
        @Override
        public int priority() {
            return 1;
        }

        @Override
        public BigDecimal compute(BigDecimal num1, BigDecimal num2) {
            return num1.subtract(num2);
        }
    },

    /**
     * 乘
     */
    MULTIPLY {
        @Override
        public int priority() {
            return 2;
        }

        @Override
        public BigDecimal compute(BigDecimal num1, BigDecimal num2) {
            return num1.multiply(num2);
        }
    },

    /**
     * 除
     */
    DIVIDE {
        @Override
        public int priority() {
            return 2;
        }

        @Override
        public BigDecimal compute(BigDecimal num1, BigDecimal num2) {
            return num1.divide(num2, 8, BigDecimal.ROUND_HALF_EVEN);
        }
    },

    /**
     * 指数
     */
    EXPONENT{
        @Override
        public int priority() {
            return 3;
        }

        @Override
        public BigDecimal compute(BigDecimal num1, BigDecimal num2) {
            BigDecimal temp = num1;
            if (num2.doubleValue() == 0) {
                num1 = new BigDecimal(1);
            }
            for (int i = 1; i < num2.doubleValue();i++){
                num1 = num1.multiply(temp);
            }
            return num1;
        }
    },

    /**
     * 开次方根
     */
    RADICAL{
        @Override
        public int priority() {
            return 3;
        }

        @Override
        public BigDecimal compute(BigDecimal num1, BigDecimal num2) {
            return null;
        }
    },

    /**
     * 括号
     */
    BRACKETS {
        @Override
        public int priority() {
            return 0;
        }

        @Override
        public BigDecimal compute(BigDecimal num1, BigDecimal num2) {
            return null;
        }
    };

    /**
     * 对应的优先级
     *
     * @return
     */
    public abstract int priority();

    /**
     * 计算两个对应的运算结果
     *
     * @param num1 第一个运算数
     * @param num2 第二个运算数
     * @return
     */
    public abstract BigDecimal compute(BigDecimal num1, BigDecimal num2);
}
