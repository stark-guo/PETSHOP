package dao;

import pojo.Pet;

import java.util.List;

public interface PetDao {

    List<Pet> selectByPet(String sql,String[] param);

    /**
     * 得到所有宠物信息
     * @return
     */
    List<Pet> getAllPet();


    int updatePet(String sql,Object[] param);
}
