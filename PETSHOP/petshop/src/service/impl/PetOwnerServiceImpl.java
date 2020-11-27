package service.impl;

import dao.AccountDao;
import dao.PetDao;
import dao.PetOwnerDao;
import dao.impl.AccountDaoImpl;
import dao.impl.PetDaoImpl;
import dao.impl.PetOwnerDaoImpl;
import dao.impl.PetStoreDaoImpl;
import pojo.Pet;
import pojo.PetOwner;
import pojo.PetStore;
import service.BuyAble;
import service.PetOwnerService;
import service.PetStoreService;
import service.SellAble;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PetOwnerServiceImpl implements PetOwnerService, BuyAble, SellAble {

    private PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();

    /**
     * 宠物主人登录
     * @return
     */
    @Override
    public PetOwner login() {
        Scanner input = new Scanner(System.in);
        System.out.print("请先登录，请输入主人的名字：");
        String name = input.nextLine().trim();
        System.out.print("请输入主人的密码：");
        String password = input.nextLine().trim();
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();
        String sql = "select * from petowner where name = ? and password = ?;";
        String[] param = {name,password};
        PetOwner owner = petOwnerDao.selectByOwner(sql,param);
        if (owner != null) {
            System.out.println("------恭喜您成功登录------");
            System.out.println("------您的基本信息：------");
            System.out.println("名字：" + owner.getName());
            System.out.println("元宝数：" + owner.getMoney());
        }
        return owner;
    }

    /**
     * 获取主人现有的宠物
     * @param ownerId
     * @return
     */
    @Override
    public List<Pet> getOwnerPet(int ownerId) {
        PetDao petDao = new PetDaoImpl();
        String sql = "select * from pet where owner_id = ?";
        String[] param = {String.valueOf(ownerId)};
        List<Pet> petList = petDao.selectByPet(sql, param);
        return petList;
    }

    /**
     * 买宠物
     * @param pet
     */
    @Override
    public void buy(Pet pet) {
        String sql = "select * from petowner where id = ?";
        String param[] = {String.valueOf(pet.getOwnerId())};
        PetOwnerDao petOwnerDao = new PetOwnerDaoImpl();
        PetOwner owner = petOwnerDao.selectByOwner(sql, param);
        PetStoreService petStore = new PetStoreServiceImpl();
        int updatePet = petStore.renewPet(pet, owner, null);
        if (updatePet > 0) {
            int updateOwner = petStore.renewOwner(owner, pet, 0);
            if (updateOwner > 0) {
                int updateStore = petStore.renewStore(pet, 1);
                if (updateStore > 0) {
                    int accountInsert = petStore.renewAccount(pet, owner);
                    if (accountInsert > 0) {
                        System.out.println("台账正确插入一条信息");
                    }
                }
            }
        }
    }

    /**
     * 宠物主人卖宠物给商店
     * @param pet
     */
    @Override
    public void sell(Pet pet) {
        PetDaoImpl petDao = new PetDaoImpl();
        String updateSql = "update pet set owner_id = null where id = ?";
        Object[] param = {pet.getId()};
        int updatePet = petDao.excuteUpdate(updateSql, param);
        if (updatePet > 0) {
            String ownerSql = "select * from petowner where id = ?";
            String ownerParam[] = {String.valueOf(pet.getOwnerId())};
            PetOwnerDaoImpl petOwnerDao = new PetOwnerDaoImpl();
            PetOwner owner = petOwnerDao.selectByOwner(ownerSql, ownerParam);

            String updateOwnerSql = "update petowner set money = ? where id = ?";
            Object[] ownerParams = {(owner.getMoney() + 3), owner.getId()};
            int updateOwner = petOwnerDao.excuteUpdate(updateOwnerSql, ownerParams);
            if (updateOwner > 0) {   //更新宠物商店信息
                PetStoreServiceImpl petStoreService = new PetStoreServiceImpl();
                PetStore petStore = petStoreService.getPetStore(pet.getStoreId());

                String updateStoreSql = "update petstore set balance = ? where id = ?";
                Object[] storeParam = {(petStore.getBalance() - 3),petStore.getId()};
                PetStoreDaoImpl storeDao = new PetStoreDaoImpl();
                int updateStore = storeDao.excuteUpdate(updateStoreSql, storeParam);
                if (updateStore > 0) {   //更新宠物商店账单信息
                    String accountSql = "insert into account(deal_type,pet_id,seller_id,buyer_id,price,deal_time) values(?,?,?,?,?,?)";
                    String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    Object[] accountParam = {2,pet.getId(),pet.getStoreId(),owner.getId(),3,format};
                    AccountDao accountDao = new AccountDaoImpl();
                    int updateAccount = accountDao.updateAccount(accountSql, accountParam);
                    if (updateAccount > 0){
                        System.out.println("台账正确插入一条信息");
                    }
                }
            }
        }
    }
}
