package dao;

import pojo.PetStore;

import java.util.List;

public interface PetStoreDao {
    PetStore selectByStore(String sql,String[] param);

    /**
     * 得到所有商店信息
     * @return
     */
    List<PetStore> getAllStore();

    int updateOwner(String sql,Object[] param);
}
