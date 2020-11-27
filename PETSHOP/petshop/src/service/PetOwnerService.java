package service;

import pojo.Pet;
import pojo.PetOwner;

import java.util.List;

public interface PetOwnerService extends BuyAble,SellAble{
    /**
     * 主人登录
     * @return
     */
    PetOwner login();
    /**
     * 根据主人id获得主人所有宠物信息
     */
    List<Pet> getOwnerPet(int ownerId);
}
