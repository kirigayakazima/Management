package com.qym.dao;

import com.qym.pojo.RunningAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface RunningAccountDao extends JpaRepository<RunningAccount,Integer> {
    List<RunningAccount> findAllByAccountId(Integer id);
}
