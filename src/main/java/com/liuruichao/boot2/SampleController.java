package com.liuruichao.boot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * SampleController
 *
 * @author liuruichao
 * Created on 2018/4/13 13:25
 */
@RestController
@EnableAutoConfiguration
public class SampleController {
    private FileChannel fileChannel;
    @GetMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/test")
    String test() throws InterruptedException, FileNotFoundException {
        File data = new File("/Users/liuruichao/tmp/test.data");
        fileChannel = new RandomAccessFile(data, "rw").getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(10 * 1024 * 1024);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                try {
                    fileChannel.read(buffer);
                    buffer.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return "success";
    }

    @GetMapping("/test2")
    String test2() throws InterruptedException, FileNotFoundException {
        File data = new File("/Users/liuruichao/tmp/test.data");
        fileChannel = new RandomAccessFile(data, "rw").getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(10 * 1024 * 1024);
        for (int i = 0; i < 100; i++) {
            Thread.sleep(100);
            new Thread(() -> {
                try {
                    fileChannel.read(buffer);
                    buffer.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        return "success";
    }

    @GetMapping("/gc")
    String gc() {
        System.gc();
        return "success";
    }

    @GetMapping("/ygc")
    String ygc() {
        LocalDateTime dateTime = null;
        for (int i = 0; i < 100000; i++) {
            dateTime = LocalDateTime.now();
        }
        return dateTime.toString();
    }

    @GetMapping("/close")
    String closeChannel() throws IOException {
        if (Objects.nonNull(fileChannel)) {
            fileChannel.close();
        }

        return "success";
    }

    @GetMapping("/user")
    User getUser() {
        return new User(1L, "liuruichao", 20);
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class, args);
    }
}
