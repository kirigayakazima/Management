package com.qym.service.Impl;

import com.qym.dao.KindDao;
import com.qym.pojo.Kind;
import com.qym.service.KindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KindServiceImpl implements KindService {
    @Autowired
    private KindDao kindDao;

    @Override
    public List<Kind> findAll() {
        return kindDao.findAll();
    }

    @Override
    public Kind findByKindName(String kindName) {
        return kindDao.findByKindName(kindName);
    }

    @Transactional
    @Override
    public Kind save(Kind kind) {
        return kindDao.save(kind);
    }

    @Override
    public int getMaxId() {
        return kindDao.getMaxId();
    }

    @Override
    public Kind getById(Integer id) {
        return kindDao.getById(id);
    }

    @Override
    public void delete(Integer id) {
        kindDao.deleteById(id);
    }

    @Override
    public Kind update(Kind kind) {
        return kindDao.save(kind);
    }

    @Override
    public List<Kind> findAllByName(String kindName) {
        return kindDao.findAllByKindName(kindName);
    }


}
