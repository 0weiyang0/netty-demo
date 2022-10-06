package com.test.nio.channeldemo;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/4 21:33
 *
 * MappedByteBuffer 可以直接让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
      RandomAccessFile randomAccessFile = new RandomAccessFile("file01.txt","rw");
      FileChannel fileChannel = randomAccessFile.getChannel();
        /**
         * FileChannel.MapMode.READ_WRITE   表示使用的是读写模式
         * 0  可以直接修改的起始位置
         * 5   映射到内存的大小，即将文件的多少个字节映射到内存
         *      代表最多5个字节
         */
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0,(byte)'h');
        map.put(3,(byte)'9');

        //Exception in thread "main" java.lang.IndexOutOfBoundsException
        map.put(5, (byte) '哈');
    }
}
