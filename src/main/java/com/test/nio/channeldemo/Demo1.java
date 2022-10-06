package com.test.nio.channeldemo;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/4 20:37
 *
 * 要求：  利用buffer和channel将  “hello,尚硅谷”写入file01.txt中
 */
public class Demo1 {

    public static void main(String[] args) throws Exception {
        String msg = "hello,尚硅谷";
        //创建一个输出流--》channel
        FileOutputStream fos = new FileOutputStream("file01.txt");
        //通过fileOutputStream ,获取通道
        FileChannel fileChannel = fos.getChannel();
        //channel在跟数据交互的时候，是需要一个缓冲的
        //就是数据先放入缓冲中，然后在放入通道中，在放入文件中
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(msg.getBytes());
        //byteBuffer进行flip
        byteBuffer.flip();
        //把缓冲区中的数据写入到通道中
        fileChannel.write(byteBuffer);
        fos.close();
    }

}
