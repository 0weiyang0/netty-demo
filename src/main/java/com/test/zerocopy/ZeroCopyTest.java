package com.test.zerocopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/6 15:57
 */
public class ZeroCopyTest {
    public static void main(String[] args) throws Exception{
//           copyByMmap("nio.txt","nio_new.txt");
        copyBySendfile("nio.txt","nio_new.txt");
    }


    //  copy方法：  从原文件拷贝到目标文件的过程      mmap是通过内存映射的方式来拷贝文件
    public static void copyByMmap(String sourceName,String destName) throws Exception{

        //  创建文件
        File source = new File(sourceName);
        File dest = new File(destName);

        //创建文件的输入输出流
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);

        //  获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        //  通过mmap的方式进行拷贝
        MappedByteBuffer mappedByteBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY,
                                                            0, source.length());
        outChannel.write(mappedByteBuffer);

        mappedByteBuffer.clear();

        fis.close();
        inChannel.close();

        outChannel.close();
        outChannel.close();
    }

    //  效率更高
    public static void copyBySendfile(String sourceName,String destName) throws Exception{
        //  创建文件
        File source = new File(sourceName);
        File dest = new File(destName);

        //创建文件的输入输出流
        FileInputStream fis = new FileInputStream(source);
        FileOutputStream fos = new FileOutputStream(dest);

        //  获取通道
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();
        inChannel.transferTo(0,inChannel.size(),outChannel);

        inChannel.close();
        fis.close();

        outChannel.close();
        fos.close();
    }
}

