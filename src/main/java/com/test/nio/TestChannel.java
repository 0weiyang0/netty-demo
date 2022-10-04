package com.test.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/4 12:59
 */
public class TestChannel {
    public static void main(String[] args) throws Exception {
        File file = new File("nio.txt");
        if (!file.exists()) file.createNewFile();
        FileOutputStream os = new FileOutputStream(file);
        //  获取通道
        FileChannel channel = os.getChannel();
        //分配缓冲大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        String msg = "hello nio";
        //放入到字节数组中
        byteBuffer.put(msg.getBytes());
        //刷新
        byteBuffer.flip();
        channel.write(byteBuffer);

        channel.close();
        os.close();
    }
}
