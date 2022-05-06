package com.example.mappingSyntax.dynamicMappingSyntaxComponent.test;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.mappingSyntax.dynamicMappingSyntaxComponent.entities.Shop;

public class executor {

	private static final Log log = LogFactory.getLog(executor.class);
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String resource = "com/example/mappingSyntax/dynamicMappingSyntaxComponent/resources/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try {

			System.out.println("[DELETE]");

			sqlSession.delete("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.delete");

			System.out.println();
			System.out.println("[INSERT]");

			Shop shop_1 = new Shop(1, "shop1", "Korea", "Y");
			Shop shop_2 = new Shop(2, "shop2", "Korea", "Y");
			Shop shop_3 = new Shop(3, "shop3", "Korea", "Y");
			Shop shop_4 = new Shop(4, "shop4", "Japan", "Y");
			Shop shop_5 = new Shop(5, "shop5", "Japan", "Y");
			Shop shop_6 = new Shop(6, "shop5", "Japan", "Y");

			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_1);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_2);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_3);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_4);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_5);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_6);

			System.out.println();
			System.out.println("[UPDATE] <if>");

			for (int i = 1; i <= 6; i++) {

				Shop shop_update = new Shop();
				shop_update.setShopNo(i);
				shop_update.setShopStatus("N");
				shop_update.setShopLocation("England");

				sqlSession.update(
						"com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.update_if",
						shop_update);
			}

			System.out.println();
			System.out.println("[INSERT]");

			Shop shop_7 = new Shop(7, "shop7", "Korea", "Y");
			Shop shop_8 = new Shop(8, "shop8", "Korea", "Y");
			Shop shop_9 = new Shop(9, "shop9", "Korea", "Y");
			Shop shop_10 = new Shop(10, "shop10", "Japan", "Y");
			Shop shop_11 = new Shop(11, "shop11", "Japan", "Y");
			Shop shop_12 = new Shop(12, "shop12", "Japan", "Y");

			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_7);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_8);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_9);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_10);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_11);
			sqlSession.insert("com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insertShop",
					shop_12);

			System.out.println();
			System.out.println("[UPDATE] <choose>");

			for (int i = 7; i <= 12; i++) {

				Shop shop_update = new Shop();
				shop_update.setShopNo(i);
				shop_update.setShopName("changed");
				shop_update.setShopStatus("N");
				shop_update.setShopLocation("England");

				sqlSession.update(
						"com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.update_choose",
						shop_update);
			}

			System.out.println();
			System.out.println("[INSERT]");

			Shop shop_13 = new Shop(13, "shop13", "Korea", "Y");
			Shop shop_14 = new Shop(14, "shop14", "Korea", "Y");
			Shop shop_15 = new Shop(15, "shop15", "Korea", "Y");
			Shop shop_16 = new Shop(16, "shop16", "Japan", "Y");
			Shop shop_17 = new Shop(17, "shop17", "Japan", "Y");
			Shop shop_18 = new Shop(18, "shop18", "Japan", "Y");

			List<Shop> list = new ArrayList<Shop>();
			list.add(shop_13);
			list.add(shop_14);
			list.add(shop_15);
			list.add(shop_16);
			list.add(shop_17);
			list.add(shop_18);

			sqlSession.insert(
					"com.example.mappingSyntax.dynamicMappingSyntaxComponent.mappers.ShopMapper.insert_foreach", list);

			System.out.println();
			System.out.println("[COMMIT]");

			sqlSession.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
}