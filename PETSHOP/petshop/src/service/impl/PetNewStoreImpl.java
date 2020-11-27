package service.impl;

import dao.PetStoreDao;
import dao.impl.PetStoreDaoImpl;
import service.PetNewStore;

import java.util.Scanner;

public class PetNewStoreImpl implements PetNewStore {
    @Override
    public void openNewStore() {
        Scanner input = new Scanner(System.in);
        System.out.println("请输入新宠物商店的信息：");
        System.out.println("请输入商店名称：");
        String name = input.nextLine();
        System.out.println("请输入商店登录密码（不超过16位）：");
        String password = input.nextLine();
        System.out.println("请输入商店的原始资金：");
        String balance = input.nextLine();
        String sql = "insert into petstore(name,password,balance) values(?,?,?)";
        Object[] param = {name,password,balance};
        PetStoreDao storeDao = new PetStoreDaoImpl();
        int insert = storeDao.updateOwner(sql, param);
        if (insert > 0) {
            System.out.println("新宠物商店创建成功，题名为"+name);
        }
    }
}
