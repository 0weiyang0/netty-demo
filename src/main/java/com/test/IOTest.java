package com.test;

import com.sun.xml.internal.fastinfoset.util.PrefixArray;

import java.io.*;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/2 22:44
 */
public class IOTest {
    public static void main(String[] args) throws Exception {
        File file = new File("io.txt");
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[is.available()];
        while(true){
           int read = is.read(bytes);
           if (read == -1) break;
            System.out.println(new String(bytes,0,read));
        }
        is.close();





















        //在内存中创建了一个文件夹
        //传递的路径为相对路径
//        File file = new File("io.txt");
//        if (!file.exists()){
//            file.createNewFile();
//        }
    //    String str = "hello io again";
        //创建一个输出流，输出文件的流
//        OutputStream os = new FileOutputStream(file);
//        //将字符串写入到流中
//        os.write(str.getBytes());
//        os.flush();
//        os.close();

        //读出字节数据
//        InputStream is = new FileInputStream(file);
//
//        byte[] arr = new byte[(int)file.length()];
//        System.out.println();
//        int size = is.read(arr);
//        for (byte b : arr) {
//           //   读出来的b   ASCII
//            System.out.print(b);
//        }
//        System.out.println("读取数据的大小："+size+"数据内容"+ new String(arr));
//        is.close();

//        copy("io.txt","io_new.txt");
    }
    //设置一个方法，copy一个文件
    public static void copy(String srcName,String destName) throws Exception {
        //  读取src数据，然后放到一个新的文件中
        File src = new File(srcName);
        File dest = new File(destName);
        if (!dest.exists()){
            dest.createNewFile();
        }
        InputStream is = new FileInputStream(src);
        OutputStream os = new FileOutputStream(dest);
        byte[] bytes = new byte[(int)src.length()];
        is.read(bytes);
        //现在将byte的内容写入到dest中
        os.write(bytes);
        os.flush();
        os.close();
        is.close();
    }
}
