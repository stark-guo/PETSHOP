package service.impl;

import dao.*;
import dao.impl.*;
import pojo.*;
import service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PetStoreServiceImpl implements PetStoreService, BuyAble, SellAble, BreedAble, AccountAble {

    /**
     * 查询账目
     * @param storeId
     * @return
     */
    @Override
    public List<Account> getAccount(int storeId) {
        String sql = "select * from account where deal_type = ? and seller_id = ? union select * from account where deal_type = ? and buyer_id = ?";
        String[] param = {"1",String.valueOf(storeId),"2",String.valueOf(storeId)};
        AccountDao accountDao = new AccountDaoImpl();
        List<Account> storeAccount = accountDao.getStoreAccount(sql, param);
        return storeAccount;
    }


    /**
     * 孵化新宠物
     * @param typeName
     * @return
     */
    @Override
    public Pet breed(String typeName) {
        //输入新孵化宠物的信息：
        Scanner input = new Scanner(System.in);
        System.out.println("请输入新孵化宠物的信息");
        System.out.println("输入新宠物名称：");
        String name = input.nextLine();
        System.out.println("输入宠物健康值：");
        String health = input.nextLine();
        System.out.println("输入宠物爱心值：");
        String love = input.nextLine();
        System.out.println("输入孵化宠物的商店编号：");
        String storeId = input.nextLine();
        String[] petParam = {name,typeName,health,love,storeId};

        //更新宠物信息，把新孵化的宠物添加到库存
        PetFactory petFactory = new PetFactoryImpl();
        Pet pet = petFactory.breedNewPet(petParam);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String sql = "insert into pet(name,type_name,health,love,birthday,store_id) values(?,?,?,?,?,?)";
        Object[] param = {pet.getName(),pet.getTypeName(),pet.getHealth(),pet.getLove(),pet.getBirthday(),pet.getStoreId()};
        PetDao petDao = new PetDaoImpl();
        int update = petDao.updatePet(sql, param);
        if (update>0) {
            System.out.println("成功孵化出一只叫"+pet.getName()+"新宠物！！！");
        }
        return pet;
    }

    /**
     * 商店登录
     * @return
     */
    @Override
    public PetStore login() {
        Scanner input = new Scanner(System.in);
        //输入宠物商店的登录信息
        PetStore ps = null;
        boolean isEnter = true;
        while (isEnter){
        System.out.print("请先登录，请输入宠物商店登录名：");
        String name = input.next().trim();
        System.out.print("请输入宠物商店的登录密码：");
        String password = input.next().trim();
        PetStoreDao psd = new PetStoreDaoImpl();
        String sql = "select * from petstore where name = ? and password = ?";
        String[] param = {name,password};
        ps = psd.selectByStore(sql,param);
            if (ps != null) {
                System.out.println("------恭喜成功登录------");
                System.out.println("------宠物商店的基本信息：------");
                System.out.println("名字：" + ps.getName());
                System.out.println("元宝数：" + ps.getBalance());
                isEnter = false;
            }else {
                System.out.println("登录失败，请重新确认您的用户名和密码：");
            }
        }
        return ps;
    }

    /**
     * 宠物定价
     * @param pet
     * @return
     */
    @Override
    public double charge(Pet pet) {
        Date date = new Date();
        double price = 0;
        int age = date.getYear() - pet.getBirthday().getYear();
        if (age>5){
            price = 3;
        }else {
            price = 5;
        }
        return price;
    }

    /**
     * 查询新培育宠物
     * @param
     * @return
     */
    @Override
    public List<Pet> getPetsBreed() {
        PetDao petDao = new PetDaoImpl();
        String sql = "select * from pet where owner_id is null and type_name not in (?,?)";
        String[] param = {"cat","dog"};
        List<Pet> list = petDao.selectByPet(sql, param);
        return list;
    }

    /**
     * 查询库存宠物
     * @param storeId
     * @return
     */
    @Override
    public List<Pet> getPetsInStock(int storeId) {
        PetDao petDao = new PetDaoImpl();
        String[] param = {String.valueOf(storeId)};
        String sql = "";
        // 当storeId为0时，要执行查询所有商店的库存宠物
        if (storeId==0){
            sql = "select * from pet where owner_id is null ;";
            param = null;
        }
        // 当storeId不为0时，要执行查询指定商店库存宠物
        if (storeId!=0){
            sql = "select * from pet where owner_id is null and store_id = ?;";
        }
        List<Pet> petList = petDao.selectByPet(sql,param);
        return petList;
    }

    /**
     * 更新宠物信息
     * @param pet
     * @param owner
     * @param store
     * @return
     */
    @Override
    public int renewPet(Pet pet, PetOwner owner, PetStore store) {
        String sql = "";
        int id = 0;
        if (owner == null){
            sql = "update pet set owner_id = null,store_id = ? where id = ? ";
            id = store.getId();
        } else if (store == null) {
            sql = "update pet set owner_id = ? where id = ?";
            id = owner.getId();
        }
        Object[] param = {id,pet.getId()};
        PetDaoImpl petDao = new PetDaoImpl();
        int update = petDao.excuteUpdate(sql, param);
        return update;
    }

    /**
     * 更新宠物主人信息
     * @param owner
     * @param pet
     * @param choose
     * @return
     */
    @Override
    public int renewOwner(PetOwner owner, Pet pet, int choose) {
        String sql = "update petowner set money = ? where id = ?";
        double price = charge(pet);
        double num = 0;
        if (choose == 0){
            num = owner.getMoney() - price;
        }
        if (choose == 1) {
            num = owner.getMoney() + price;
        }
        Object[] param = {num,owner.getId()};
        PetOwnerDaoImpl petOwnerDao = new PetOwnerDaoImpl();
        int update = petOwnerDao.excuteUpdate(sql, param);
        return update;
    }

    /**
     *更新宠物商店信息
     * @param pet
     * @param choose
     * @return
     */
    @Override
    public int renewStore(Pet pet, int choose) {
        PetStore petStore = getPetStore(pet.getStoreId());
        String sql = "update petstore set balance = ? where id = ?";
        double price = charge(pet);
        double num = 0;
        if (choose == 0){
            num = (petStore.getBalance()-price);
        }
        if (choose == 1) {
            num = (petStore.getBalance()+price);
        }
        Object[] param = {num,petStore.getId()};
        PetStoreDaoImpl storeDao = new PetStoreDaoImpl();
        int update = storeDao.excuteUpdate(sql, param);
        return update;
    }

    /**
     * 更新账单信息
     * @param pet
     * @param owner
     * @return
     */
    @Override
    public int renewAccount(Pet pet, PetOwner owner) {
        String sql = "insert into account(deal_type,pet_id,seller_id,buyer_id,price,deal_time) values(?,?,?,?,?,?);";
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        double price = charge(pet);
        Object[] param = {1,pet.getId(),pet.getStoreId(),owner.getId(),price,date};
        AccountDao accountDao = new AccountDaoImpl();
        int accountInsert = accountDao.updateAccount(sql, param);
        return accountInsert;
    }

    /**
     *根据商店编号查询宠物商店
     * @param id
     * @return
     */
    @Override
    public PetStore getPetStore(int id) {
        String sql = "select * from petstore where id =" + id;
        PetStoreDao storeDao = new PetStoreDaoImpl();
        PetStore petStore = storeDao.selectByStore(sql, null);
        return petStore;
    }

    /**
     * 得到宠物主人正在出售的宠物
     * @return
     */
    @Override
    public List<Pet> getSelling() {
        PetDaoImpl petDao = new PetDaoImpl();
        String sql = "select * from pet where owner_id is not null";
        String param[] = null;
        List<Pet> list = petDao.selectByPet(sql, param);
        return list;
    }

    /**
     * 购买宠物
     * @param pet
     */
    @Override
    public void buy(Pet pet) {
        String sql = "select * from petstore where id = ?";
        String storeParam[] = {String.valueOf(pet.getStoreId())};
        PetStoreDao storeDao = new PetStoreDaoImpl();
        PetStore petStore = storeDao.selectByStore(sql, storeParam);
        PetOwnerDao ownerDao = new PetOwnerDaoImpl();
        sql = "select * from petowner where id = ?";
        String ownerParam[] = {String.valueOf(pet.getOwnerId())};
        PetOwner owner = ownerDao.selectByOwner(sql, ownerParam);
        int updatePet = renewPet(pet, null, petStore);  //更新宠物信息

        if (updatePet>0) {   //更新宠物主人信息
            int updateOwner = renewOwner(owner, pet, 1);
            if (updateOwner>0){  //更新宠物商店信息
                int updateStore = renewStore(pet,0);
                if (updateStore>0) {  //更新宠物商店账单信息
                    int insertAccount = renewAccount(pet, owner);
                    if (insertAccount>0) {
                        System.out.println("台账正确插入一条信息");
                    }
                }
            }
        }else {
            System.out.println("购买失败！！！");
        }
    }


    /**
     * 售卖宠物
     * @param pet
     */
    @Override
    public void sell(Pet pet) {
        PetDaoImpl petDao = new PetDaoImpl();
        PetStoreServiceImpl petStoreService = new PetStoreServiceImpl();

        String updateSql = "update pet set store_id = null,owner_id = ? where id = ?";
        Object[] param = {pet.getOwnerId(),pet.getId()};
        int updatePet = petDao.excuteUpdate(updateSql, param); //更新宠物信息
        if (updatePet>0) {  //更新宠物主人信息
            PetOwnerDaoImpl ownerDao = new PetOwnerDaoImpl();
            String ownerSql = "select * from petowner where id = ?";
            String ownerParams[] = {String.valueOf(pet.getOwnerId())};
            PetOwner owner = ownerDao.selectByOwner(ownerSql, ownerParams);

            String updateOwnerSql = "update petowner set money = ? where id = ?";
            double charge = petStoreService.charge(pet);
            Object[] ownerParam = {(owner.getMoney()-charge),owner.getId()};
            int updateOwner = ownerDao.excuteUpdate(updateOwnerSql, ownerParam);
            if (updateOwner > 0) { //更新宠物商店信息
                PetStoreDaoImpl storeDao = new PetStoreDaoImpl();
                PetStore store = petStoreService.getPetStore(pet.getStoreId());

                String updateStoreSql = " update petstore set balance = ? where id = ?";
                Object[] storeParam = {(store.getBalance()+charge),store.getId()};
                int updateStore = storeDao.excuteUpdate(updateStoreSql, storeParam);

                if (updateStore > 0){ //更新商店账单信息
                    String accountSql = "insert into account(deal_type,pet_id,seller_id,buyer_id,price,deal_time) values(?,?,?,?,?,?)";
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    Object[] accountParam = {1,pet.getId(),pet.getStoreId(),owner.getId(),charge,date};
                    AccountDaoImpl accountDao = new AccountDaoImpl();
                    int updateAccount = accountDao.updateAccount(accountSql, accountParam);

                    if (updateAccount > 0) {
                        System.out.println("台账正确插入一条信息");
                    }
                }
            }
        }
    }


}
