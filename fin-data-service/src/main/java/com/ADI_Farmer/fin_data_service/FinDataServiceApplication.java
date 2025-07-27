package com.ADI_Farmer.fin_data_service;

import com.ADI_Farmer.fin_data_service.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinDataServiceApplication implements CommandLineRunner {

	@Autowired
	private DashboardService dashboardService;

	public static void main(String[] args) {
		SpringApplication.run(FinDataServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Initialize default dashboard configurations
		dashboardService.initializeDefaultConfigs();
		System.out.println("✅ Financial Data Aggregator API started successfully!");
		System.out.println("🌐 Dashboard available at: http://localhost:8082");
		System.out.println("📊 API Documentation at: http://localhost:8082/swagger-ui.html");
	}
}
