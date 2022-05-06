package com.example.introducingMybatis.bindingWithList.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.example.introducingMybatis.bindingWithList.entities.Shop;
import com.example.introducingMybatis.bindingWithList.mapper.Application;



public class Executor {
	private static final Logger logger = Logger.getLogger(Executor.class.getName());

	public static void main(String[] args) {
		try {
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(1);
			parameters.add("Y");

			Application application = new Application();
			Shop shop = application.view(parameters);

			logger.info(shop.getShopName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
