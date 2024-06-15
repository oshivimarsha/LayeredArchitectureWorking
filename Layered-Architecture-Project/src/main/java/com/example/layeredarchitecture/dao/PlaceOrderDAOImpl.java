package com.example.layeredarchitecture.dao;

import com.example.layeredarchitecture.db.DBConnection;
import com.example.layeredarchitecture.model.ItemDTO;
import com.example.layeredarchitecture.model.OrderDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlaceOrderDAOImpl implements PlaceOrderDAO{
    ItemDAO itemDAO = new ItemDAOImpl();
    OrderDAO orderDAO = new OrderDAOImpl();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    @Override
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        /*Transaction*/
        Connection connection = DBConnection.getDbConnection().getConnection();
        try {

            int stm =  orderDAO.saveOrder(orderId, orderDate, customerId);

            if (stm != 1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            for(OrderDetailDTO detail : orderDetails) {
                stm = orderDetailDAO.saveOrderDetail(detail,orderId);

                if (stm != 1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
                ItemDTO item = itemDAO.findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                boolean pstm = itemDAO.updateItem(item);

                if (!(pstm)) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
