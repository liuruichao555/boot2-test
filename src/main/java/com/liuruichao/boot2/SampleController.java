package com.liuruichao.boot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SampleController
 *
 * @author liuruichao
 * Created on 2018/4/13 13:25
 */
@RestController
@EnableAutoConfiguration
public class SampleController {
    @GetMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/user")
    User getUser() {
        return new User(1L, "liuruichao", 20);
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleController.class, args);
    }
}
