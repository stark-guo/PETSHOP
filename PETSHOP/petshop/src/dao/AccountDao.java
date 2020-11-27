package dao;


import pojo.Account;

import java.util.List;

public interface AccountDao {
   int updateAccount(String sql,Object[] param);

   List<Account> getStoreAccount(String sql,String[] param);
}
