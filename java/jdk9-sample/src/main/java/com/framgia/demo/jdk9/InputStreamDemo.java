package com.framgia.demo.jdk9;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputStreamDemo {

    public static void main(String[] args) throws IOException {
        // readAllBytes
        System.out.println(getDataFromIsJava8(ClassLoader.getSystemResourceAsStream("story.txt")));
        System.out.println(getDataFromIsApacheCommons(ClassLoader.getSystemResourceAsStream("story.txt")));
        System.out.println(getDataFromIsJava9(ClassLoader.getSystemResourceAsStream("story.txt")));

        // readNBytes
        System.out.println(readNBytes(ClassLoader.getSystemResourceAsStream("story.txt")));

        // transferTo
        OutputStream os = new ByteArrayOutputStream();
        ClassLoader.getSystemResourceAsStream("story.txt").transferTo(os);
        System.out.println(os);
    }

    private static String getDataFromIsJava8(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[8192];
        try {
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                baos.write(data, 0, nRead);
            }
            baos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(baos.toByteArray());
    }

    private static String getDataFromIsApacheCommons(InputStream is) {
        try {
            return new String(IOUtils.toByteArray(is));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getDataFromIsJava9(InputStream is) {
        try {
            return new String(is.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readNBytes(InputStream is) {
        byte[] partialData = new byte[20];
        try {
            is.readNBytes(partialData, 0, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(partialData);
    }
}
