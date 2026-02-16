package com.project.inventoryservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import java.util.List;
//import org.springframework.boot.CommandLineRunner;
//import com.project.inventoryservice.model.Inventory;
//import com.project.inventoryservice.repository.InventoryRepository;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
		System.out.println("INVENTORY-SERVICE started at PORT : 8083");
	}
	
	
	
//	 @Bean
//	    CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//	        return args -> {
//
//	            Inventory inventory1 = new Inventory();
//	            inventory1.setSkuCode("iphone13");
//	            inventory1.setQuantity(100);
//
//	            Inventory inventory2 = new Inventory();
//	            inventory2.setSkuCode("iphone14");
//	            inventory2.setQuantity(50);
//
//	            inventoryRepository.saveAll(List.of(inventory1, inventory2));
//	        };
//	 }
}
