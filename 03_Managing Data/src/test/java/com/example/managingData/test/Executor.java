package com.example.managingData.test;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.example.managingData.entities.Shop;

public class Executor {
	private static final Log log = LogFactory.getLog(Executor.class);

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String resource = "com/example/managingData/resources/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try {
			Shop shop = new Shop();

			// delete
			shop.setShopNo(4);

			sqlSession.delete("org.mybatis.persistence.ShopMapper.delete", shop);

			// select
			shop.setShopNo(3);

			shop = sqlSession.selectOne("org.mybatis.persistence.ShopMapper.select", shop);
			log.debug(shop.getShopName());

			// insert
			shop.setShopNo(5);
			shop.setShopName("izakaya");
			shop.setShopLocation("Japan");
			shop.setShopStatus("Y");

			sqlSession.insert("org.mybatis.persistence.ShopMapper.insert", shop);

			// update
			shop.setShopNo(1);
			shop.setShopStatus("N");

			sqlSession.update("org.mybatis.persistence.ShopMapper.update", shop);

			// commit
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();

			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
	}
}
