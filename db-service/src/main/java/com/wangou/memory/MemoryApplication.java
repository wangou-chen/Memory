package com.wangou.memory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 注解自动扫描 com.wangou.memory 及所有子包的组件
@SpringBootApplication
public class MemoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemoryApplication.class, args);
	}

}