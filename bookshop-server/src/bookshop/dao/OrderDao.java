package bookshop.dao;

import bookshop.entity.Order;

import java.util.List;

public interface OrderDao {

    List<Order> getOrderList(String status, String query, int pagenum, int pagesize) throws Exception;

    Order getOrderInfo(int orderNo) throws Exception;

    int getOrderTotal() throws Exception;

}
