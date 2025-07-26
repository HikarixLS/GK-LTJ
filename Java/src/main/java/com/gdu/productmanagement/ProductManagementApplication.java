package com.gdu.productmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class ProductManagementApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("🚀 Starting Book Store Management System...");
        System.out.println("========================================================");
        SpringApplication.run(ProductManagementApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String port = env.getProperty("server.port", "8080");
        String contextPath = env.getProperty("server.servlet.context-path", "");
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎉 APPLICATION STARTED SUCCESSFULLY!");
        System.out.println("=".repeat(60));
        System.out.println("🌐 URL: http://localhost:" + port + contextPath);
        System.out.println("📚 Book Store Management System is ready!");
        System.out.println("🛑 Press Ctrl+C to stop the application");
        System.out.println("=".repeat(60) + "\n");
    }
}
