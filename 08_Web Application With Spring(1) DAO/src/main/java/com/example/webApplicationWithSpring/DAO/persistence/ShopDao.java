package com.example.webApplicationWithSpring.DAO.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webApplicationWithSpring.DAO.entities.Shop;

public interface ShopDao {
    public List<Shop> list(Shop shop);
    public void insert(Shop shop);
    public Shop select(String shopNo);
    public void update(Shop shop);
    public void delete(String shopNo);
}
