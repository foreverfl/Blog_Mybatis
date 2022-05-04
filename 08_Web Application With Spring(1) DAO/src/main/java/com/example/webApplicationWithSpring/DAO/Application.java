package com.example.webApplicationWithSpring.DAO;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.webApplicationWithSpring.DAO.persistence.ShopDao;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public ShopDao shopDao;

	public Application(ShopDao shopDao) {
		this.shopDao = shopDao;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

	}

}