package com.wb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author ylx
 * @version 1.0
 * @date 2022/3/12 9:48
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserMainStart {

    public static void main(String[] args) {
        SpringApplication.run(UserMainStart.class, args);
    }

}
