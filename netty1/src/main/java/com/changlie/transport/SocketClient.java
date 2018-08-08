package com.changlie.transport;

import java.io.*;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) throws Exception {
        Socket so = new Socket("127.0.0.1",8888);

        DataInputStream dis = new DataInputStream(so.getInputStream());


        DataOutputStream dos = new DataOutputStream(so.getOutputStream());


        InputStreamReader isr = new  InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        while(true){
            String str = br.readLine();
            //1 没有通知服务器
            dos.writeUTF(str);
            if("bye".equalsIgnoreCase(str)) break;
            String s = dis.readUTF();
            System.out.println(s);
        }
        dis.close();
        dos.close();
        so.close();
    }
}
