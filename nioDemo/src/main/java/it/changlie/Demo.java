package it.changlie;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Demo {

    @Test
    public void test() throws Exception{
        JSONObject obj = new JSONObject();
        obj.fluentPut("name", "changlie");
        System.out.println(obj.toJSONString());
        URL url = getClass().getResource("");
        System.out.println(url.getPath());
        test1(url.getPath()+"t.txt");
    }

    private void test1(String filePath) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile(filePath, "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {

            System.out.println(">>>>>>>>>>>>>>>>>>Read " + bytesRead);
            buf.flip();

            while (buf.hasRemaining()) {
                System.out.print((char) buf.get());
            }
            System.out.println();

            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        aFile.close();
        System.out.println("normally close");
    }
}
