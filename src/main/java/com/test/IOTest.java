package com.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/2 22:44
 */
public class IOTest {
    public static void main(String[] args) throws Exception {
        //在内存中创建了一个文件夹
        //传递的路径为相对路径
        File file = new File("io.txt");
        if (!file.exists()){
            file.createNewFile();
        }
        String str = "hello io again";
        //创建一个输出流，输出文件的流
        OutputStream os = new FileOutputStream(file);
        //将字符串写入到流中
        os.write(str.getBytes());
        os.flush();
        os.close();
    }

}
