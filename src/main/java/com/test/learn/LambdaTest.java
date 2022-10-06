package com.test.learn;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/5 9:09
 */
public class LambdaTest {

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }
    //lambda  允许把函数作为一个方法的参数
    // （函数作为参数传递进方法中）

    //   语法：   (parameters) -> expression
    //        |   (parameter)->{expression}

    public static void main(String[] args) {
        LambdaTest lambdaTest = new LambdaTest();
        //  类型声明
        MathOperation addition = (int a,int b) -> a+b;
    }

}
