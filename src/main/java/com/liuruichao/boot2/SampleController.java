package com.liuruichao.boot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import java.util.*;

/**
 * SampleController
 *
 * @author liuruichao
 * Created on 2018/4/13 13:25
 */
@RestController
@EnableAutoConfiguration
public class SampleController {
    private List<ByteBuffer> buffers = new ArrayList<>();
    private FileChannel fileChannel;
    private static Map<String, User> map = new HashMap<>();

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

    @GetMapping("/test3")
    public String test3() {
        List<User> users = new ArrayList<>(1100000);
        for (int i = 0; i < 1000000; i++) {
            users.add(new User((long) i, "liuruichao" + i, i));
        }

        String str = "hehe: " + users;
        return "test3";
    }

    @GetMapping("/test4")
    public String test4(@RequestParam Integer count) {
        for (int i = 0; i < count; i++) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 60);
            buffers.add(buffer);
        }
        return "test4";
    }

    @GetMapping("/remove/buffers")
    public String removeBuffers() {
        buffers = new ArrayList<>();
        return "remove buffers success";
    }

    @GetMapping("/test5")
    public String test5() {
        for (int i = 0; i < 100000; i++) {
            User user = new User(i + System.currentTimeMillis(), System.currentTimeMillis() + "liuruichao", 20);
            map.put(user.toString(), user);
        }

        return "success";
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class, args);
    }
}
