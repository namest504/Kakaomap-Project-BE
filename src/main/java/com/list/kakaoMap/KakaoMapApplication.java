package com.list.kakaoMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KakaoMapApplication {

	public static void main(String[] args) {
		SpringApplication.run(KakaoMapApplication.class, args);
	}

}
