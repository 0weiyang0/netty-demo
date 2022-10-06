package com.test.nio.channeldemo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/4 21:06
 *
 * transform  拷贝文件
 */
public class Demo2 {
    public static void main(String[] args) throws Exception{
        //创建相关的流
        FileInputStream is = new FileInputStream("a.jpg");
        FileOutputStream os = new FileOutputStream("a2.jpg");

        FileChannel sourceCh = is.getChannel();
        FileChannel destCh = os.getChannel();

        destCh.transferFrom(sourceCh,0,sourceCh.size());

        sourceCh.close();
        destCh.close();
        os.close();
        is.close();

    }
}
