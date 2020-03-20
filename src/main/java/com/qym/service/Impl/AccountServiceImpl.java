package com.qym.service.Impl;

import com.qym.dao.AccountDao;
import com.qym.pojo.Account;
import com.qym.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public Account findById(Integer id) {
        return accountDao.findById(id).get();
    }

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }
}
