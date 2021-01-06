package bookshop.dao.impl;

import bookshop.dao.OrderDao;
import bookshop.entity.Order;
import bookshop.utils.DBHelper;
import bookshop.vo.ResultVO;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> getOrderList(String status, String query, int pagenum, int pagesize) throws Exception {
        if (pagenum <= 0 || pagenum == 1) {
            pagenum = 0;
        } else {
            pagenum = (pagenum - 1) * pagesize;
        }

        Connection conn = DBHelper.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        String sql = "select book_order.*," +
                "B.productNo,B.bookName,B.author,B.price,B.cover, " +
                "C.userNo,C.userName,C.phone, " +
                "D.addressNo,D.consignee,D.detail_address,D.tel " +
                "from book_order " +
                "left join book_commodity as B on B.productNo = book_order.productNo " +
                "join book_user as C  on C.userNo = book_order.userNo " +
                "join user_address as D on D.addressNo = book_order.addressNo " +
                "where 1=1 ";
        Object[] params = null;
        //查询条件
        if (status == "" && query == "") {
            sql += " order by createTime desc " +
                    "offset ? rows " +
                    "fetch next ? rows only";
            params = new Object[]{pagenum, pagesize};
        } else if (status != "") {
            sql += " and order_status = ?";
            sql += " order by createTime desc " +
                    "offset ? rows " +
                    "fetch next ? rows only";
            params = new Object[]{status, pagenum, pagesize};
        } else if (query != "") {   //收货人 or 联系电话
            sql += "and consignee like ? or tel like ? ";
            sql += " order by createTime desc " +
                    "offset ? rows " +
                    "fetch next ? rows only";
            params = new Object[]{"%" + query + "%", "%" + query + "%", pagenum, pagesize};
        } else if (status != "" && query != "") {
            sql += " and status = ?";
            sql += "and consignee like ? or tel like ? ";
            sql += " order by createTime desc " +
                    "offset ? rows " +
                    "fetch next ? rows only";
            params = new Object[]{status, "%" + query + "%", "%" + query + "%", pagenum, pagesize};
        }


        try {
            return queryRunner.query(conn, sql, new BeanListHandler<>(Order.class), params);
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }

    @Override
    public Order getOrderInfo(int orderNo) throws Exception {
        Connection conn = DBHelper.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        String sql = "select book_order.*," +
                "B.productNo,B.bookName,B.author,B.price,B.cover, " +
                "C.userNo,C.userName,C.phone, " +
                "D.addressNo,D.consignee,D.detail_address,D.tel " +
                "from book_order " +
                "left join book_commodity as B on B.productNo = book_order.productNo " +
                "join book_user as C  on C.userNo = book_order.userNo " +
                "join user_address as D on D.addressNo = book_order.addressNo " +
                "where orderNo=? ";
        try {
            return queryRunner.query(conn, sql, new BeanHandler<>(Order.class), orderNo);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    @Override
    public int getOrderTotal() throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "select  count(*) total from book_order";
        try {
            return (int) new QueryRunner().query(conn, sql, new ScalarHandler<>("total"));
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static void main(String[] args) throws Exception {
        List<Order> orderList = new OrderDaoImpl().getOrderList("", "", 1, 8);
        int orderTotal = new OrderDaoImpl().getOrderTotal();
        System.out.println(ResultVO.success(orderList, "万事大吉").setPage(1, 8, orderTotal).toJSON());

    }
}
