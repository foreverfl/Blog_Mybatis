package com.example.introducingMybatis.bindingWithList.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.example.introducingMybatis.bindingWithList.entities.Shop;

public class Application extends SqlMapper {
	public Shop view(List<Object> parameters) throws Exception {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Shop shop = null;

		try {
			String sql = parameterBindingByList("select", parameters);

			preparedStatement = connect().prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				shop = new Shop();
				shop.setShopNo(resultSet.getInt("SHOP_NO"));
				shop.setShopName(resultSet.getString("SHOP_NAME"));
				shop.setShopLocation(resultSet.getString("SHOP_LOCATION"));
				shop.setShopStatus(resultSet.getString("SHOP_STATUS"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			release();
		}

		return shop;
	}
}
