package com.qym.service.Impl;

import com.qym.dao.RunningAccountDao;
import com.qym.pojo.RunningAccount;
import com.qym.service.RunningAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunningAccountServiceImpl implements RunningAccountService {
    @Autowired
    private RunningAccountDao runningAccountDao;

    @Override
    public List<RunningAccount> findAllByAccountId(Integer id) {
        return runningAccountDao.findAllByAccountId(id);
    }
}
