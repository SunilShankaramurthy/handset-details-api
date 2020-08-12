package com.practice.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.practice.mobile")
public class ApplicationLauncher {
  public static void main(String[] args) {
    SpringApplication.run(ApplicationLauncher.class, args);
  }
}
