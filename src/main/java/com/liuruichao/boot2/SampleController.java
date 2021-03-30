package com.liuruichao.boot2;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("/")
    String home() {
        Integer n = null;
        for (int i = 0; i < 100000; i++) {
            try {
                int a = n / 0;
            } catch (Exception e) {
                logger.error("error {}!", i, e);
            }
        }
        return "Hello World!";
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("heh");
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class, args);
    }
}
