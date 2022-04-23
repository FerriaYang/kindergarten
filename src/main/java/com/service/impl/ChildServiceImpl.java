package com.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Child;
import com.mapper.ChildDao;
import com.service.ChildService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Damon
 * @since 2022-02-08
 */
@Service
public class ChildServiceImpl extends ServiceImpl<ChildDao, Child> implements ChildService {

    @Autowired
    private ChildDao childDao;

    @Override
    public Page<Child> getChild(String name, Page page) {
        return childDao.selectChild(name,page);
    }

    @Override
    public Child getChildById(Integer id) {
        return childDao.selectChildById(id);
    }

    @Override
    public Page<Child> getChildByGradeId(String name, Page page, Integer id) {
        return childDao.selectChildByGradeId(name, page, id);
    }

    @Override
    public Page<Child> getChildByParentId(String name, Page page, Integer id) {
        return childDao.selectChildByParentId(name, page, id);
    }

    @Override
    public List<Child> getChildByParentId(String name, Integer id) {
        return childDao.selectChildByParentId(name, id);
    }
}
