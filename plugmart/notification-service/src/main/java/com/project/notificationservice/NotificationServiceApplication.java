package com.project.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

import com.project.notificationservice.event.OrderPlaceEvent;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class NotificationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
		System.out.println("NOTIFICATION-SERVICE started at PORT : 8085");
	}

	@KafkaListener(topics = "notificationTopic")
	public void handleNotification(OrderPlaceEvent orderPlaceEvent) {
		//send email notification 
		log.info("Received Notification for Order -{}", orderPlaceEvent.getOrderNumber() );
	}
	
}
