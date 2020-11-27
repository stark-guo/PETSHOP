package dao.impl;

import dao.BaseDao;
import dao.PetOwnerDao;
import pojo.PetOwner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetOwnerDaoImpl extends BaseDao implements PetOwnerDao {
    private Connection connection ;
    private PreparedStatement ps;
    private ResultSet rs;



    @Override
    public PetOwner selectByOwner(String sql, String[] param) {
        PetOwner petOwner = null;
        try {
             connection = openConnection();
             ps = connection.prepareStatement(sql);
            if (ps!=null) {
                for (int i = 0; i < param.length; i++) {
                    ps.setString(i+1,param[i]);
                }
            }
             rs = ps.executeQuery();
            if (rs.next()) {
                petOwner = new PetOwner();
                petOwner.setId(rs.getInt("id"));
                petOwner.setName(rs.getString("name"));
                petOwner.setPassword(rs.getString("password"));
                petOwner.setMoney( rs.getDouble("money"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(connection,ps,rs);
        }
        return petOwner;
    }

    @Override
    public List<PetOwner> getAllOwner() {
        ArrayList<PetOwner> ownerList = new ArrayList<PetOwner>();
        try {
            connection = openConnection();
            String sql = "select * from petowner";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PetOwner po = new PetOwner();
                po.setId(rs.getInt("id"));
                po.setName(rs.getString("name"));
                po.setPassword(rs.getString("password"));
                po.setMoney(rs.getDouble("money"));
                ownerList.add(po);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(connection,ps,rs);
        }
        return ownerList;
    }

    @Override
    public int updateOwner(String sql, String[] param) {
        int count = excuteUpdate(sql,param);
        return count;
    }
}
