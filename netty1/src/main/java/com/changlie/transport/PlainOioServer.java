package com.changlie.transport;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class PlainOioServer {

    public static void serve(int port) throws IOException {
        // 绑定服务器到指定的端口。
        final ServerSocket socket = new ServerSocket(port);     //1
        try {
            for (;;) {
                // 接受一个连接。
                final Socket clientSocket = socket.accept();    //2
                System.out.println("Accepted connection from " + clientSocket);

                // 创建一个新的线程来处理连接。
                new Thread(()->{
                    OutputStream out;
                    try {
                        // 将消息发送到连接的客户端。
                        out = clientSocket.getOutputStream();
                        printInput(clientSocket.getInputStream());
                        out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));                            //4
                        out.flush();
                        //一旦消息被写入和刷新时就 关闭连接。
                        clientSocket.close();                //5

                    } catch (IOException e) {
                        e.printStackTrace();
                        try {
                            clientSocket.close();
                        } catch (IOException ex) {
                            // ignore on close
                        }
                    }
                }).start();   //6启动线程。
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printInput(InputStream inputStream) throws IOException {
        BufferedReader r  = new BufferedReader(new InputStreamReader(inputStream));
        String line ;
        while ((line = r.readLine())!=null){
            System.out.println("receive: "+line);
        }

    }

    public static void main(String[] args) throws IOException {
        serve(8888);
    }
}