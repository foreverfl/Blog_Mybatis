package com.example.webApplicationWithSpring.DAO.service;

import java.util.List;

import com.example.webApplicationWithSpring.DAO.entities.Shop;

public interface ShopService {
    public List<Shop> find(Shop shop);
    public void add(Shop shop);
    public Shop view(String shopNo);
    public void edit(Shop shop);
    public void remove(String shopNo);
}
