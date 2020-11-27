package dao.impl;

import dao.AccountDao;
import dao.BaseDao;
import pojo.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl extends BaseDao implements AccountDao {
    private Connection connection ;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int updateAccount(String sql, Object[] param) {
        int update = excuteUpdate(sql, param);
        return update;
    }

    @Override
    public List<Account> getStoreAccount(String sql, String[] param) {
        ArrayList<Account> list= new ArrayList<>();

        try {
            connection = openConnection();
            ps = connection.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    ps.setString(i+1,param[i]);
                }
            }
            rs = ps.executeQuery();
            while (rs.next()){
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setDealType(rs.getInt("deal_type"));
                account.setPetId(rs.getInt("pet_id"));
                account.setBuyerId(rs.getInt("buyer_id"));
                account.setSellerId(rs.getInt("seller_id"));
                account.setPrice(rs.getDouble("price"));
                account.setDealTime(rs.getDate("deal_time"));
                list.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(connection,ps,rs);
        }
        return list;
    }
}
