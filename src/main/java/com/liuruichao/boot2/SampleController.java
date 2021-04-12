package com.liuruichao.boot2;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
//    @Resource
    private TranslationCommands translationCommands;

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

    @GetMapping("/test6")
    public List<String> test6() {
        String[] array = new String[500];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(random.nextLong());
        }

        return buildList(Function.identity(), StringUtils::isNotBlank, array);
    }

    @GetMapping("/test7")
    public String test7() throws InterruptedException {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            User user = new User(i + System.currentTimeMillis(), System.currentTimeMillis() + "liuruichao", 20);
            users.add(user);
        }

        String str = JSON.toJSONString(users);
        Thread.sleep(1000 * 3);
        return "success";
    }

    @GetMapping("/test8")
    public String testShell1() {
        return translationCommands.translate();
    }

    public <T, R> List<R> buildList(Function<T, R> function, Predicate<R> predicate, T... t) {
        return Arrays.stream(t).map(function).filter(predicate).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class, args);
    }
}
