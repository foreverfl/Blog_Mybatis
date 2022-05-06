package com.example.introducingMybatis.bindingWithMap.test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.example.introducingMybatis.bindingWithMap.entities.Shop;
import com.example.introducingMybatis.bindingWithMap.mapper.Application;

public class Executor {
	private static final Logger logger = Logger.getLogger(Executor.class.getName());

	public static void main(String[] args) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("shopNo", 1);
			parameters.put("shopStatus", "Y");

			Application application = new Application();
			Shop shop = application.view(parameters);

			logger.info(shop.getShopName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
