package com.test.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author 臧娜
 * @version 1.0
 * @date 2022/10/3 16:04
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",1234);
        String message = "hello socket";
        OutputStream os = socket.getOutputStream();
        os.write(message.getBytes());
        socket.close();
        os.close();
    }
}
