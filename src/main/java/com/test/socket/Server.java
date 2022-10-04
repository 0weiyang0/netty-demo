package com.test.socket;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/3 15:47
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *   服务端
 */
public class Server {
    public static void main(String[] args) throws IOException {
        //创建一个线程池，，这个线程池是可缓冲的，如果有，则复用，如果没有，就创建
        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(1234);

        //这个时候，不断轮询查看是否有客户端的连接
        while(true){
            //有客户端的连接了，这时候接收
            //final  表示不可变，确保线程安全
          final Socket socket = serverSocket.accept();
          executorService.execute(new Runnable() {
              public void run() {
                  handler(socket);
              }
          });
        }

    }

    private static void handler(Socket socket)  {
        System.out.println(Thread.currentThread().getId());
        System.out.println(Thread.currentThread().getName());

        //接收客户端的数据
        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
              int read = inputStream.read(bytes);
              System.out.println(new String(bytes,0,read));
//            int read = inputStream.read(bytes);
//            String message = new String(bytes);
//           System.out.println("客户端传递过来的消息："+message);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
