package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {
    public int saveOrder(String orderId, LocalDate orderDate, String customerId) throws SQLException, ClassNotFoundException;
    public String generateId() throws SQLException, ClassNotFoundException;
}
