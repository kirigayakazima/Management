package com.qym.dao;

import com.qym.pojo.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface KindDao extends JpaRepository<Kind,Integer> {
    Kind findByKindName(String kindName);

//    @Query("select max(id) from kind")
//    Kind getMaxId();
//@Query("select user from User u where u.userId = (select max(userId) from User)")
//User getMaxUserById();

    @Query("select max(id) from Kind")
    int getMaxId();

    Kind getById(Integer id);

    List<Kind> findAllByKindName(String kindName);

}
