package dao;

import pojo.PetOwner;

import java.util.List;

public interface PetOwnerDao {
    PetOwner selectByOwner(String sql,String[] param);

    /**
     * 获取所有主人信息
     * @return
     */
    List<PetOwner> getAllOwner();

    /**
     * 更新宠物信息
     * @param sql
     * @param param
     * @return
     */
    int updateOwner(String sql,String[] param);
}
