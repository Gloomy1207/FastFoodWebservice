package com.gloomy;

import com.gloomy.service.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class FastFoodWebserviceApplication implements CommandLineRunner {
    @Resource
    private StorageService mStorageService;

    public static void main(String[] args) {
        SpringApplication.run(FastFoodWebserviceApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        mStorageService.init();
    }
}
