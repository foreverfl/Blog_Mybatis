package com.example.introducingMybatis.bindingWithMap.mapper;

import java.util.Map;

import com.example.introducingMybatis.bindingWithMap.entities.Shop;

public class Application extends SqlMapper {
	public Shop view(Map<String, Object> parameters) throws Exception {
		return selectOne("select", parameters, "com.example.introducingMybatis.bindingWithMap.entities.Shop");
	}
}
