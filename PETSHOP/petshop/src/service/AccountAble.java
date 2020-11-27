package service;

import pojo.Account;
import pojo.Pet;
import pojo.PetOwner;

import java.util.List;

public interface AccountAble {
    List<Account> getAccount(int storeId);

    int renewAccount(Pet pet, PetOwner owner);
}
