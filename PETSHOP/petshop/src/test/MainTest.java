package test;


import dao.*;
import dao.impl.*;
import pojo.Account;
import pojo.Pet;
import pojo.PetOwner;
import pojo.PetStore;
import service.PetFactory;
import service.PetNewStore;
import service.PetOwnerService;
import service.PetStoreService;
import service.impl.PetFactoryImpl;
import service.impl.PetNewStoreImpl;
import service.impl.PetOwnerServiceImpl;
import service.impl.PetStoreServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTest {
    //  private Scanner input;
    //  private static boolean isOk = true;

    public static void main(String[] args) {
        systemStart();
    }

    /**
     * 启动宠物商店系统
     */
    public static void systemStart() {
        //宠物信息
        System.out.println("宠物商店启动");
        System.out.println("所有宠物从MySql中醒来");
        System.out.println("*****************************");
        System.out.println("序号\t宠物名称");
        PetDao petDao = new PetDaoImpl();
        List<Pet> petList = petDao.getAllPet();
        for (int i = 0; i < petList.size(); i++) {
            Pet pet = petList.get(i);
            System.out.println((i + 1) + "\t\t" + pet.getName());
        }
        System.out.println("*****************************\n");
        //宠物主人信息
        System.out.println("所有宠物主人从MySql中醒来");
        System.out.println("*****************************");
        System.out.println("序号\t主人姓名");
        PetOwnerDao ownerDao = new PetOwnerDaoImpl();
        List<PetOwner> ownerList = ownerDao.getAllOwner();
        for (int i = 0; i < ownerList.size(); i++) {
            PetOwner owner = ownerList.get(i);
            System.out.println((i + 1) + "\t\t" + owner.getName());
        }
        System.out.println("*****************************\n");
        //宠物商店信息
        System.out.println("所有宠物商店从MySql中醒来");
        System.out.println("*****************************");
        System.out.println("序号\t宠物商店名称");
        PetStoreDao storeDao = new PetStoreDaoImpl();
        List<PetStore> storeList = storeDao.getAllStore();
        for (int i = 0; i < storeList.size(); i++) {
            PetStore store = storeList.get(i);
            System.out.println((i + 1) + "\t\t" + store.getName());
        }
        System.out.println("*****************************\n");
        Scanner input = new Scanner(System.in);
        System.out.println("请选择登录模式，输入1为宠物主人登录，输入2为宠物商店登录：");
        boolean isOk = true;
        int i = 0;
        while (isOk) {
            i = input.nextInt();
            if (i == 1) {
                //宠物主人登录
                ownerLogin();
                isOk = false;
            } else if (i == 2) {
                //宠物商店登录
                storeLogin();
                isOk = false;
            }else  if (i != input.nextInt()) {
                System.out.println("请输入正确选项：");
                System.out.println("请选择登录模式，输入1为宠物主人登录，输入2为宠物商店登录：");
                isOk = true;
            } else {
                System.out.println("请输入正确的选项：");
                System.out.println("请选择登录模式，输入1为宠物主人登录，输入2为宠物商店登录：");
                isOk = true;
            }
        }
    }

    /**
     * 宠物主人登录
     * @return
     */
    public static PetOwner ownerLogin() {
        Scanner input = new Scanner(System.in);
        PetOwnerService pos = new PetOwnerServiceImpl();
        PetOwner po = pos.login();
        boolean isEnter = true;
        while (isEnter) {
            if (po == null) {
                System.out.println("登录失败，请重新确认您的用户名和密码：");
                po = pos.login();
                isEnter = true;
            } else {
                isEnter = false;
                System.out.println("登录成功，您可以购买和卖出宠物，如果您想要购买宠物请输入1，如果想要卖出宠物请输入2：");
                System.out.println("1：购买宠物");
                System.out.println("2：卖出宠物");
                boolean isOk = true;
                int choose = input.nextInt();
                while (isOk){
                    if (choose==1){
                        ownerBuy(po);
                        isOk = false;
                    }else if (choose == 2){
                        ownerSell(po);
                        isOk = false;
                    }else {
                        System.out.println("请按提示输入正确的选项");
                        isOk = true;
                    }
                }
            }
        }
        return po;
    }

    /**
     * 宠物商店登录
     * @return
     */
    public static PetStore storeLogin(){
        Scanner input = new Scanner(System.in);
        PetStoreService pss = new PetStoreServiceImpl();
        PetStore store = pss.login();
        int num = -1;
                boolean isOk = false;
                while (!isOk) {
                System.out.println("登录成功，可以进行如下操作：");
                System.out.println("1：购买宠物");
                System.out.println("2：卖出宠物");
                System.out.println("3：培育宠物");
                System.out.println("4：查询待售宠物");
                System.out.println("5：查看商店结余");
                System.out.println("6：查看商店账目");
                System.out.println("7：开宠物商店");
                System.out.println("请根据需要执行的操作，选择序号输入，退出请输入0");
                    int choice = input.nextInt();
                    switch (choice) {
                        case 0:
                            System.out.println("退出系统！");
                            isOk = true;
                            break;
                        case 1:
                            System.out.println("购买宠物");
                            storeBuy(store);
                            break;
                        case 2:
                            System.out.println("卖出宠物");
                            storeSell(store);
                            break;
                        case 3:
                            System.out.println("培育宠物");
                            newBreed();
                            break;
                        case 4:
                            System.out.println("查询待售宠物");
                            checkStayPet(store.getId());
                            break;
                        case 5:
                            System.out.println("查看商店结余");
                            checkStoreBalance(store);
                            break;
                        case 6:
                            System.out.println("查看商店账目");
                            checkStoreDeal(store.getId());
                            break;
                        case 7:
                            System.out.println("开宠物商店");
                            openNewStore();
                            break;
                    }
                    if (!isOk) {
                        System.out.println("是否继续操作，输入8继续，9退出！");
                        num = input.nextInt();
                        if (num == 8) {
                            continue;
                        }else if (num == 9){
                            System.out.println("退出成功！！！");
                            break;
                        }
                    }else {
                        System.out.println("退出成功！！！");
                        break;
                    }
                }
        return store;
    }

    /**
     * 宠物主人从商店购买宠物
     * @param petOwner
     */
    public static void ownerBuy(PetOwner petOwner) {
        Scanner input = new Scanner(System.in);
        PetStoreService petStoreService = new PetStoreServiceImpl();
        PetOwnerService petOwnerService = new PetOwnerServiceImpl();
        Pet pet = null;
        System.out.println("------请选择购买范围：只输入选项序号：------");
        System.out.println("1：购买库存宠物");
        System.out.println("2：购买新培育宠物");
        int choose = input.nextInt();
        List<Pet> list = null;
        int num = -1;
        double price = 0;
        boolean isOk = true;
        while (isOk) {
            if (choose == 1) {
                list = petStoreService.getPetsInStock(0);
                System.out.println("------以下是库存宠物信息------");
                System.out.println("序号\t宠物名称\t类型\t元宝数");
                for (int i = 0; i < list.size(); i++) {
                    pet = list.get(i);
                    price = petStoreService.charge(pet);
                    System.out.println((i + 1) + "\t\t" + pet.getName() + "\t" + pet.getTypeName() + "\t" + price);
                }
                System.out.println("------请选择要购买哪一个宠物，并输入选择项的序号------");
                choose = input.nextInt();
                if (choose <= 0 || choose > list.size() + 1) {
                    System.out.println("请输入正确的序号：");
                    isOk = true;
                } else if (choose > 0 || choose < list.size() + 1) {
                    pet = list.get(choose - 1);
                    pet.setOwnerId(petOwner.getId());
                    petOwnerService.buy(pet);
                    isOk = false;
                }
                isOk = false;
            } else if (choose == 2) {
                list = petStoreService.getPetsBreed();
                System.out.println("------以下是新培育的宠物------");
                System.out.println("序号\t宠物名称\t类型\t元宝数");
                for (int i = 0; i < list.size(); i++) {
                    pet = list.get(i);
                    price = petStoreService.charge(pet);
                    System.out.println((i + 1) + "\t\t" + pet.getName() + "\t\t" + pet.getTypeName() + "\t\t" + price);
                }
                System.out.println("------请选择要购买哪一个宠物，并输入选择项的序号------");
                String count = input.next();
                if (count.matches("[0-9]*")) {
                    choose = Integer.parseInt(count);
                    if (choose <= 0 || choose > list.size() + 1) {
                        System.out.println("请输入正确的序号：");
                        isOk = true;
                    } else if (choose > 0 || choose < list.size() + 1) {
                        pet = list.get(choose - 1);
                        pet.setOwnerId(petOwner.getId());
                        petOwnerService.buy(pet);
                        isOk = false;
                    }
                    isOk = false;
                }
            } else {
                System.out.println("请按提示输入正确的选项：");
                isOk = false;
            }
            if (!isOk) {
                System.out.println("是否继续，输入0继续，9退出");
                 num = input.nextInt();
                if (num == 0) {
                    isOk = true;
                    continue;
                }else if (num == 9){
                    System.out.println("退出成功！！！");
                    break;
                }
            }else {
                System.out.println("退出成功！！！");
                break;
            }
        }

    }

    /**
     * 宠物主人卖宠物
     * @param petOwner
     */
    public static void ownerSell(PetOwner petOwner){
        Scanner input = new Scanner(System.in);
        PetOwnerService owner = new PetOwnerServiceImpl();
        System.out.println("------我的宠物列表------");
        List<Pet> list = owner.getOwnerPet(petOwner.getId());
        System.out.println("序号\t宠物名称\t类型");
        for (int i = 0; i <list.size() ; i++) {
            Pet pet = list.get(i);
            System.out.println((i+1)+"\t\t"+pet.getName()+"\t"+pet.getTypeName());
        }
        System.out.println("------选择您要卖出的宠物的序号------");
        boolean isOk = true;
        int number = 0;
        while (isOk) {
           number = input.nextInt();
            if ((number - 1) < list.size() && (number - 1) >= 0) {
                Pet pet = list.get(number - 1);
                System.out.println("------请确认您要卖出的宠物信息------");
                System.out.println("宠物名字是：" + pet.getName() + ",宠物类型是：" + pet.getTypeName());
                System.out.println("请确认是否卖出！(确认/取消):");
                String choose = input.next();
                if (choose != null) {
                    if (choose.equals("确认")) {
                        System.out.println("------请选择您要卖给商店的序号------");
                        List<PetStore> storeList = new ArrayList<>();
                        PetStoreDao storeDao = new PetStoreDaoImpl();
                        storeList = storeDao.getAllStore();
                        PetStore petStore = null;
                        System.out.println("序号"+"\t"+"宠物商店名");
                        for (int i = 0; i < storeList.size(); i++) {
                            petStore = storeList.get(i);
                            System.out.println((i+1)+"\t\t"+petStore.getName());
                        }
                        number = input.nextInt();
                        if ((number - 1) < storeList.size() && (number - 1) >= 0) {
                            petStore = storeList.get(number - 1);
                        }
                        pet.setStoreId(petStore.getId());
                        owner.sell(pet);
                    } else if (choose.equals("取消")) {
                        System.out.println("------您选择放弃本次交易，欢迎下次光临------");
                    }else {
                        System.out.println("------您输入的信息有误------");
                    }
                }
                isOk = true;
            }else {
                System.out.println("输入错误，请按指示重新输入:");
                isOk = false;
            }
            isOk = false; //退出系统
        }
    }

    /**
     * 宠物商店购买宠物
     */
    public static void storeBuy(PetStore store){
        Scanner input = new Scanner(System.in);
        PetStoreService petStore = new PetStoreServiceImpl();
        Pet pet = null;
        List<Pet> list = petStore.getSelling();
        System.out.println("------以下是宠物主人正在出售的宠物------");
        System.out.println("序号\t宠物名称\t宠物类型\t宠物价格");
        for (int i = 0; i < list.size(); i++) {
            pet = list.get(i);
            double price = petStore.charge(pet);
            System.out.println((i+1)+"\t\t"+pet.getName()+"\t\t"+pet.getTypeName()+"\t\t"+price);
        }
        System.out.println("------请输入购买宠物的序号------");
        int choose = input.nextInt();
        pet = list.get(choose - 1);
        petStore.buy(pet);
    }

    /**
     * 宠物商店卖出宠物
     */
    public static void storeSell(PetStore store){
        Scanner input = new Scanner(System.in);
        PetStoreService petStore = new PetStoreServiceImpl();
        Pet pet = null;
        double price = 0;
        System.out.println("------宠物商店正在出售的宠物------");
        System.out.println("序号\t宠物名称\t宠物类型\t宠物价格");
        List<Pet> list = petStore.getPetsInStock(store.getId());
        for (int i = 0; i < list.size(); i++) {
            pet = list.get(i);
             price = petStore.charge(pet);
            System.out.println((i+1)+"\t\t"+pet.getName()+"\t\t"+pet.getTypeName()+"\t\t"+price);
        }
        System.out.println("请选择您要购买的宠物编号：");
        int choose = input.nextInt();
        boolean isOk = true;
        while (isOk){
            if ((choose - 1) < list.size() && (choose -1) >= 0){
                pet = list.get(choose - 1);
                 price = petStore.charge(pet);
                System.out.println("这是您要卖出的宠物信息：");
                System.out.println("宠物名称:"+pet.getName()+";宠物类型:"+pet.getTypeName()+";宠物价格:"+price);
                System.out.println("请核对宠物信息，确认是否卖出该宠物（确认/取消）：");
                String choice = input.next();
                if (choice != null) {
                    if (choice.equals("确认")) {
                        System.out.println("请您选择要你卖给的宠物主人序号：");
                        List<PetOwner> ownerList = new ArrayList<>();
                        PetOwnerDao ownerDao = new PetOwnerDaoImpl();
                        ownerList = ownerDao.getAllOwner();
                        PetOwner petOwner = null;
                        System.out.println("序号\t宠物主人名字");
                        for (int i = 0; i < ownerList.size(); i++) {
                            petOwner = ownerList.get(i);
                            System.out.println((i + 1) + "\t\t" + petOwner.getName());
                        }
                        choose = input.nextInt();
                        if ((choose - 1) < ownerList.size() && (choose - 1) >= 0) {
                            petOwner = ownerList.get(choose - 1);
                        }
                        pet.setOwnerId(petOwner.getId());
                        petStore.sell(pet);
                    } else if (choice.equals("取消")) {
                        System.out.println("您选择放弃本次交易！！！");
                    } else {
                        System.out.println("输入的信息有误：");
                    }
                }
                isOk = true;
            }else {
                System.out.println("输入错误，请按指示重新输入：");
                isOk = false;
            }
            isOk = false;
        }
    }

    /**
     * 宠物商店培养新宠物
     */
    public static void newBreed() {
        Scanner input = new Scanner(System.in);
        PetStoreService petStore = new PetStoreServiceImpl();
        System.out.println("请输入培养的新宠物类型：");
        String type = input.next();
        petStore.breed(type);
    }

    /**
     * 查询待出售的宠物信息
     */
    public static void checkStayPet(int storeId) {
        Pet pet = null;
        PetStoreService petStore = new PetStoreServiceImpl();
        List<Pet> list = petStore.getPetsInStock(storeId);
        System.out.println("序号\t宠物名称\t宠物类型");
        //double price = petStore.charge(pet);
        for (int i = 0; i < list.size(); i++) {
            pet = list.get(i);
            System.out.println((i+1)+"\t\t"+pet.getName()+"\t "+pet.getTypeName());
        }
    }

    /**
     * 查询商店金额结余
     */
    public static void checkStoreBalance(PetStore store) {
        double balance = store.getBalance();
        System.out.println(store.getName()+"商店的金额结余是："+balance);
    }

    /**
     * 查询商店的账单
     */
    public static void checkStoreDeal(int storeId) {
        PetStoreServiceImpl petStore = new PetStoreServiceImpl();
        Account account = null;
        String type = null;
        List<Account> list = petStore.getAccount(storeId);
        for (int i = 0; i < list.size(); i++) {
            account = list.get(i);
            if (account.getDealType() == 1){
                type = "宠物主人去宠物商店购买宠物";
            } else if (account.getDealType() ==2) {
                type = "宠物主人卖宠物给宠物商店";
            }else {
                type = "宠物主人与宠物主人之间的交易";
            }
            System.out.println("第"+(i+1)+"几笔交易，交易类型为："+account.getDealType()
                    +",交易金额为："+account.getPrice()+",交易时间为："+account.getDealTime());
        }
    }

    /**
     *
     * 开办一家新宠物店
     */
    public static void openNewStore() {
        //实例化对象
        PetNewStore petNewStore = new PetNewStoreImpl();
        //调用开新店方法
        petNewStore.openNewStore();
    }


}





