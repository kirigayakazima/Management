package com.qym.service;

import com.qym.pojo.Kind;

import java.util.List;

public interface KindService {
    List<Kind> findAll();

    Kind findByKindName(String kindName);

    Kind save(Kind kind);

    int getMaxId();

    Kind getById(Integer id);

    void delete(Integer id);

    Kind update(Kind kind);

    List<Kind> findAllByName(String kindName);
}
