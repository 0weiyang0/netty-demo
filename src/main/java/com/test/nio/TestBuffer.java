package com.test.nio;

import java.nio.CharBuffer;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/4 13:14
 */
public class TestBuffer {
    public static void main(String[] args) {
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        System.out.println("position:"+charBuffer.position());     //0
        System.out.println("limit:"+charBuffer.limit());           //1024
        System.out.println("capacity"+charBuffer.capacity());       //1024
        //  ========存放一些数据======================
        System.out.println("存放一些数据之后的大小");
        charBuffer.put('a');
        charBuffer.put('b');
        System.out.println("position:"+charBuffer.position());      //2
        System.out.println("limit:"+charBuffer.limit());            //1024
        System.out.println("capacity"+charBuffer.capacity());        //1024
        //   使用flip()方法之后
        System.out.println("调用flip之后");
        charBuffer.flip();
        System.out.println("position:"+charBuffer.position());      //0
        System.out.println("limit:"+charBuffer.limit());            //2
        System.out.println("capacity"+charBuffer.capacity());        //1024
        //  读取数据
        System.out.println("=========>读取数据：");
        System.out.println(charBuffer.get(0));      //a
//        System.out.println(charBuffer.get());       //a  这里注意的是，不传参代表读取的是第一个值，传参代表索引位置
        //  get(0) 与get()的区别就是传递参数，position不改变，不传参数，position发生改变
        System.out.println("position:"+charBuffer.position());

        while(charBuffer.hasRemaining()){
            System.out.println(charBuffer.get());
        }
        //clear  清空的是索引位置，对象依然存在
        charBuffer.clear();
        System.out.println("调用clear======>");
        System.out.println("position:"+charBuffer.position());      //0
        System.out.println("limit:"+charBuffer.limit());            //1024
        System.out.println("capacity"+charBuffer.capacity());        //1024
        System.out.println("遍历buffer数据=============================================");
    }
}
