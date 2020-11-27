package dao.impl;

import dao.BaseDao;
import dao.PetDao;
import pojo.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDaoImpl extends BaseDao implements PetDao {
    private Connection connection;
    private  PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Pet> selectByPet(String sql,String[] param) {
        List<Pet> petList = new ArrayList<Pet>();
        Pet pet = null;
        try {
            connection = openConnection();
            ps = connection.prepareStatement(sql);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    ps.setString(i+1,param[i]);
                }
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setTypeName(rs.getString("type_name"));
                pet.setHealth(rs.getInt("health"));
                pet.setLove(rs.getInt("love"));
                pet.setBirthday(rs.getDate("birthday"));
                pet.setOwnerId(rs.getInt("owner_id"));
                pet.setStoreId(rs.getInt("store_id"));
                petList.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(connection,ps,rs);
        }
        return petList;
    }

    @Override
    public List<Pet> getAllPet() {
        ArrayList<Pet> petList = new ArrayList<>();
        try {
            connection = openConnection();
            String sql = "select * from pet";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setName(rs.getString("name"));
                pet.setTypeName(rs.getString("type_name"));
                pet.setHealth(rs.getInt("health"));
                pet.setLove(rs.getInt("love"));
                pet.setBirthday(rs.getDate("birthday"));
                pet.setOwnerId(rs.getInt("owner_id"));
                pet.setStoreId(rs.getInt("store_id"));
                petList.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeAll(connection,ps,rs);
        }
        return petList;
    }

    @Override
    public int updatePet(String sql, Object[] param) {
        int i = super.excuteUpdate(sql, param);
        return i;
    }
}
