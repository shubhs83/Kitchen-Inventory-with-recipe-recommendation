package com.momhelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // ← ADD THIS LINE

public class MomhelpApplication {

	public static void main(String[] args) {
		SpringApplication.run(MomhelpApplication.class, args);
		System.out.println("✅ Automated expiry alerts ENABLED!");
		System.out.println("⏰ Will run daily at 9 AM and 6 PM");
	}

}
