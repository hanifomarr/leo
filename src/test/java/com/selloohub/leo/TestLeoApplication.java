package com.selloohub.leo;

import org.springframework.boot.SpringApplication;

public class TestLeoApplication {

	public static void main(String[] args) {
		SpringApplication.from(LeoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
