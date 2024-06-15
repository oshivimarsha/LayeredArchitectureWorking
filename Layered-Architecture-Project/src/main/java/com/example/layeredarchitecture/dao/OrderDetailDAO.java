package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;

public interface OrderDetailDAO {
    int saveOrderDetail(OrderDetailDTO orderDetailDTO, String orderId) throws SQLException, ClassNotFoundException;
}