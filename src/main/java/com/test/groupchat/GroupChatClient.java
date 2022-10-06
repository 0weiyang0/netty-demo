package com.test.groupchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/6 12:54
 */
public class GroupChatClient {
    private final String HOST = "127.0.0.1";
    private final int PORT = 8989;
    private SocketChannel socketChannel;
    private Selector selector;
    private String username;

    //  构造器
    public GroupChatClient() throws Exception{
      selector = Selector.open();
      socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
      // 设置为非阻塞
      socketChannel.configureBlocking(false);
      //将socketChannel注册到selector中
        socketChannel.register(selector, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + " is ok...");
    }

    //  方法，给服务端发送数据
    public void sendToServer(String info) throws Exception{
        info = username + "说：" + info;
        socketChannel.write(ByteBuffer.wrap(info.getBytes()));
    }
    // 方法，读取服务端发送过来的数据
    public void readFromServer() throws Exception{
        //  获取有事件发生的通道的个数
        int channelCount = selector.select();
        if (channelCount > 0){
            //  说明有事件的发生
            //通过selectedKeys 获取发生的事件
            Iterator<SelectionKey> keySelectionKeys = selector.selectedKeys().iterator();
            //  循环判断
            while (keySelectionKeys.hasNext()){
               SelectionKey key = keySelectionKeys.next();
               SocketChannel socketChannel =(SocketChannel) key.channel();
               ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
               socketChannel.read(byteBuffer);
               String msg = new String(byteBuffer.array());
               System.out.println("来自服务端的消息 ：" +msg);
            }
            keySelectionKeys.remove();
        }else{
//            System.out.println("没有事件发生...");
        }
    }

    public static void main(String[] args) throws Exception {
        GroupChatClient chatClient = new GroupChatClient();
        //  启动一个线程，读取从服务端发送过来的数据
        new Thread(){
          public void run(){
              while (true){
                  try {
                      chatClient.readFromServer();
                      Thread.currentThread().sleep(3000);
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
           String msg =  scanner.nextLine();
           chatClient.sendToServer(msg);
        }
    }


}
