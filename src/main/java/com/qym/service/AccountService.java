package com.qym.service;

import com.qym.pojo.Account;

import java.util.List;

public interface AccountService {

    Account findById(Integer id);

    List<Account> findAll();
}
