package com.qym.dao;

import com.qym.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountDao extends JpaRepository<Account, Integer> {
}
