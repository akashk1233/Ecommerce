package com.ecom.inventoryservice;

import com.ecom.inventoryservice.model.Inventory;
import com.ecom.inventoryservice.repository.InventoryRepo;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);

	}
	@Bean
	public CommandLineRunner loadInventory(InventoryRepo inventoryRepo)
	{
		return args -> {
			Inventory inventory1 = Inventory.builder()
					.skuCode("samsung")
					.quantity(10)
					.build();
			Inventory inventory2 = Inventory.builder()
					.skuCode("moto")
					.quantity(0)
					.build();

			inventoryRepo.save(inventory1);
			inventoryRepo.save(inventory2);
		};
	}

}
