package com.fix.quickfixjinitiator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class QuickfixjinitiatorApplication {
	private static CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(QuickfixjinitiatorApplication.class, args);
		com.fix.quickfixjinitiator.QuickFixJServerImpl quickFixJServer=applicationContext.getBean(com.fix.quickfixjinitiator.QuickFixJServerImpl.class);
		quickFixJServer.loadConfiguration();
		quickFixJServer.logon();
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
