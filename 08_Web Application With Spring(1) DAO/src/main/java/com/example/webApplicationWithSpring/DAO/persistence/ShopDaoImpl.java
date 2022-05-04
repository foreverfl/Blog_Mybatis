package com.example.webApplicationWithSpring.DAO.persistence;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.example.webApplicationWithSpring.DAO.entities.Shop;

@Repository
public class ShopDaoImpl implements ShopDao {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	/* 가게 목록 조회 */
	@Override
	public List<Shop> list(Shop shop) {
		// 마이바티스 객체 호출
		return this.sqlSessionTemplate.selectList("com.example.webApplicationWithSpring.DAO.persistence.ShopMapper.list",
				shop);
	}

	/* 가게 등록 */
	@Override
	public void insert(Shop shop) {
		// 마이바티스 객체 호출
		this.sqlSessionTemplate.insert("com.example.webApplicationWithSpring.DAO.persistence.ShopMapper.insert", shop);
	}

	/* 가게 조회 */
	@Override
	public Shop select(String shopNo) {
		// 마이바티스 객체 호출
		return this.sqlSessionTemplate.selectOne("com.example.webApplicationWithSpring.DAO.persistence.ShopMapper.select",
				shopNo);
	}

	/* 가게 수정 */
	@Override
	public void update(Shop shop) {
		// 마이바티스 객체 호출
		this.sqlSessionTemplate.update("com.example.webApplicationWithSpring.DAO.persistence.ShopMapper.update", shop);
	}

	/* 가게 삭제 */
	@Override
	public void delete(String shopNo) {
		// 마이바티스 객체 호출
		this.sqlSessionTemplate.delete("com.example.webApplicationWithSpring.DAO.persistence.ShopMapperdelete", shopNo);
	}
}
