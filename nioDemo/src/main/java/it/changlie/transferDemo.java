package it.changlie;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;

public class transferDemo {
    @Test
    public void test1() throws Exception {
        URL url = getClass().getResource("");
        System.out.println(url);
        RandomAccessFile fromF = new RandomAccessFile(url.getPath()+"from.txt", "rw");
        RandomAccessFile toF = new RandomAccessFile(url.getPath()+"to1.txt", "rwd");
        FileChannel fromChan = fromF.getChannel();
        FileChannel toChan = toF.getChannel();

        fromChan.force(true);
        long count = fromChan.size();
        System.out.println("count: "+count);
        toChan.transferFrom(fromChan, 0L, count);
        fromF.close();
        toF.close();
    }

    @Test
    public void test2() throws Exception {
        URL url = getClass().getResource("");
        System.out.println(url);
        RandomAccessFile fromF = new RandomAccessFile(url.getPath()+"from.txt", "rw");
        RandomAccessFile toF = new RandomAccessFile(url.getPath()+"to.txt", "rw");
        FileChannel fromChan = fromF.getChannel();
        FileChannel toChan = toF.getChannel();

        long count = fromChan.size();
        System.out.println("count: "+count);
        fromChan.transferTo(0, count, toChan);
        fromF.close();
        toF.close();
    }
}
