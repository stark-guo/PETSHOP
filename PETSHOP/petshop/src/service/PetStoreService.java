package service;

import pojo.Pet;
import pojo.PetOwner;
import pojo.PetStore;

import java.util.List;

public interface PetStoreService extends BuyAble,SellAble,BreedAble,AccountAble{
    //商店登录
    PetStore login();

    //宠物定价
    double charge(Pet pet);

    //查询新培育宠物
    List<Pet> getPetsBreed();

    //查询库存宠物
    List<Pet> getPetsInStock(int storeId);

    //更新宠物信息
    int renewPet(Pet pet, PetOwner owner,PetStore store);

    //更新主人信息
    int renewOwner(PetOwner owner,Pet pet,int choose);

    //更新商店信息
    int renewStore(Pet pet,int choose);

    //根据宠物商店编号查询商店
    PetStore getPetStore(int id);

    //查询宠物主人正在出售的宠物
    List<Pet> getSelling();
}
