package bookshop.dao.impl;

import bookshop.dao.UserDao;
import bookshop.entity.User;
import bookshop.utils.DBHelper;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.util.List;

/**
 * 用户类，实现 UserDao 接口(意味着要遵守接口定义的规则)
 * 在类中要重写接口中定义的方法
 * 接口是比抽象类更加纯粹，“纯规则”,对类的功能进行规定！！
 */
public class userDaoImpl implements UserDao {


    @Override
    public User addUser(User user) throws Exception {

        return null;
    }

    /**
     * 修改用户信息方法
     * @param id    根据此 id 做为条件
     * @param user
     * @return 返回 用户信息
     * @throws Exception
     */
    @Override
    public User updateUser(String id, User user) throws Exception {
        Connection conn = DBHelper.getConnection();
        try {
            QueryRunner queryRunner = new QueryRunner();
            String sql = "update book_shop " +
                    "set username = ? userpass = ? phone = ? " +
                    "where userno = ?";
            Object[] params = {
                    user.getUserName(),
                    user.getPassword(),
                    user.getPhone(),
                    id,
            };
            queryRunner.update(conn, sql, params);
            user.setUserNo(Integer.parseInt(id));
            return user;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 分页展示数据
     *
     * @param query    查询参数： 根据用户名 或 手机号查询
     * @param pagenum  查询的页数
     * @param pagesize 每页显示的行数
     * @return 返回用户列表
     * @throws Exception
     */
    @Override
    public List<User> getUsers(String query, int pagenum, int pagesize) throws Exception {
        //判断页数
        if (pagenum == 1) {
            pagenum = pagenum - 1;
        } else if (pagenum <= 0) {
            pagenum = 0;
        } else {
            pagenum = (pagenum - 1) * pagesize;

        }
        Connection conn = DBHelper.getConnection();
        QueryRunner queryRunner = new QueryRunner();
        try {
            String sql = "select userno,username,userpass,phone,createtime from book_user " +
                    "where username like ? or phone like ? " +
                    "order by userno  \n" +
                    "offset ? rows\n" +
                    "fetch next ? rows only ;";

            Object[] params = {
                    "%" + query + "%",
                    "%" + query + "%",
                    pagenum,
                    pagesize,
            };
            List<User> users = queryRunner.query(conn, sql, new BeanListHandler<>(User.class), params);
            return users;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public static void main(String[] args) throws Exception {
        userDaoImpl userDao = new userDaoImpl();
        System.out.println(userDao.getUsers("11", 0, 5));
    }
}
