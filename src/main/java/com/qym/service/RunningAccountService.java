package com.qym.service;

import com.qym.pojo.RunningAccount;

import java.util.List;

public interface RunningAccountService {

    List<RunningAccount> findAllByAccountId(Integer id);
}
