package com.example.mappingSyntax.mappingSyntaxComponent.test;

import java.io.Reader;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.mappingSyntax.mappingSyntaxComponent.entities.Shop;

public class executor {

	private static final Log log = LogFactory.getLog(executor.class);
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String resource = "com/example/mappingSyntax/mappingSyntaxComponent/resources/config-mybatis.xml";
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
			
			sqlSession.delete("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.delete");

			System.out.println();
			System.out.println("[INSERT]");
			
			Shop shop_1 = new Shop(1, "shop1", "Korea", "Y");
			Shop shop_2 = new Shop(2, "shop2", "Korea", "Y");
			Shop shop_3 = new Shop(3, "shop3", "Korea", "Y");
			Shop shop_4 = new Shop(4, "shop4", "Japan", "N");
			Shop shop_5 = new Shop(5, "shop5", "Japan", "N");

			Shop shop_6 = new Shop(); // using the 'AUTO_INCREMENT'
			shop_6.setShopName("shop6");
			shop_6.setShopLocation("England");
			shop_6.setShopStatus("N");

			sqlSession.insert("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.insertShop", shop_1);
			sqlSession.insert("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.insertShop", shop_2);
			sqlSession.insert("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.insertShop", shop_3);
			sqlSession.insert("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.insertShop", shop_4);
			sqlSession.insert("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.insertShop", shop_5);
			sqlSession.insert("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.insertShop", shop_6);

			System.out.println();
			System.out.println("[SELECT_1]");
			
			Map result_associatedSelect = sqlSession.selectOne(
					"com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.associatedSelect", 1);
			log.debug(result_associatedSelect.toString());

			System.out.println();
			System.out.println("[SELECT_2]");

			Map result_selectShopJoinToy = sqlSession.selectOne(
					"com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.selectShopJoinToy", 1);
			log.debug(result_selectShopJoinToy.toString());

			System.out.println();
			System.out.println("[UPDATE]");

			Shop shop_update = new Shop();
			shop_update.setShopLocation("England");
			shop_update.setShopStatus("Y");

			sqlSession.update("com.example.mappingSyntax.mappingSyntaxComponent.mappers.ShopMapper.update", shop_3);

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