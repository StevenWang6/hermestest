package com.hermes.poll;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.hermes.poll.service.PollService;

@SpringBootApplication
public class PollSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(PollSystemApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner initData(PollService pollService) {
        return args -> {
            System.out.println("初始化示例数据...");
            pollService.initializeSampleData();
            System.out.println("Hermes 在线投票系统启动完成！");
            System.out.println("访问地址: http://localhost:8080");
            System.out.println("H2控制台: http://localhost:8080/h2-console");
        };
    }
}