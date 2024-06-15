package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAOImpl implements OrderDetailDAO{
    @Override
    public int saveOrderDetail(OrderDetailDTO orderDetailDTO, String orderId) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)");

        pstm.setString(1, orderId);
        pstm.setString(2, orderDetailDTO.getItemCode());
        pstm.setBigDecimal(3, orderDetailDTO.getUnitPrice());
        pstm.setInt(4, orderDetailDTO.getQty());

        return pstm.executeUpdate();
    }
}