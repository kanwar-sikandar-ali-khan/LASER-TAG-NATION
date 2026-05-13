package com.lasertagnation;  // root package, lowercase

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LaserTagNationApplication {   // renamed class
    public static void main(String[] args) {
        SpringApplication.run(LaserTagNationApplication.class, args);
    }
}
