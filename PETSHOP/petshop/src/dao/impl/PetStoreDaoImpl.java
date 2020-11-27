package dao.impl;

import dao.BaseDao;
import dao.PetStoreDao;
import pojo.PetStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetStoreDaoImpl extends BaseDao implements PetStoreDao {
    private Connection connection ;
    private PreparedStatement ps;
    private ResultSet rs;


    @Override
    public PetStore selectByStore(String sql, String[] param) {
        PetStore petStore = null;
        try {
            connection = openConnection();
            ps = connection.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    ps.setString(i + 1,param[i]);
                }
            }
            rs = ps.executeQuery();
            while (rs.next()) {
               petStore = new PetStore();
               petStore.setId(rs.getInt("id"));
               petStore.setName(rs.getString("name"));
               petStore.setPassword(rs.getString("password"));
               petStore.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(connection,ps,rs);
        }
        return petStore;
    }

    @Override
    public List<PetStore> getAllStore() {
        ArrayList<PetStore> storeList = new ArrayList<PetStore>();
        try {
            connection = openConnection();
            String sql = "select * from petstore";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                PetStore p = new PetStore();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPassword(rs.getString("password"));
                p.setBalance(rs.getDouble("balance"));
                storeList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(connection,ps,rs);
        }
        return storeList;
    }

    @Override
    public int updateOwner(String sql, Object[] param) {
        int update = excuteUpdate(sql, param);
        return update;
    }
}
