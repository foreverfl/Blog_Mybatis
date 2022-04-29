package com.example.introducingMybatis.mapper;

import java.util.Map;

import com.example.introducingMybatis.entities.Shop;

public class Application extends SqlMapper {
	public Shop view(Map<String, Object> parameters) throws Exception {
		return selectOne("select", parameters, "com.example.introducingMybatis.entities.Shop");
	}
}
