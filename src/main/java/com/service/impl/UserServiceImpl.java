package com.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import com.mapper.UserDao;
import com.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ferria
 * @since 2022-02-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getByUsername(Integer username) {
        return userDao.getOneByUserName(username);
    }

    @Override
    public Page<User> getAll(Page page) {
        return userDao.selectAll(page);
    }

    @Override
    public List<User> getAll() {
        return userDao.selectAll();
    }

    @Override
    public Page<User> getLock(Page page) {
        return userDao.selectLock(page);
    }

    @Override
    public List<User> getLock() {
        return userDao.selectLock();
    }

    @Override
    public Boolean editStatusByUsername(Integer status, Integer username) {
        return userDao.updateStatusByUsername(status, username);
    }

    @Override
    public Boolean editParentByUsername(Integer parentId, Integer username) {
        return userDao.updateParentByUsername(parentId, username);
    }

    @Override
    public Boolean editTeacherByUsername(Integer teacherId, Integer username) {
        return userDao.updateTeacherByUsername(teacherId, username);
    }

    @Override
    public Boolean editImgByUsername(String img, Integer username) {
        return userDao.updateImgByUsername(img, username);
    }

    @Override
    public Boolean editPasswordByUsername(String password, Integer username) {
        return userDao.updatePasswordByUsername(password, username);
    }

    @Override
    public Boolean editNickNameByUsername(String nickName, Integer username) {
        return userDao.updateNickNameByUsername(nickName, username);
    }

    @Override
    public Boolean addUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public Boolean removeByUsername(Integer username) {
        return userDao.deleteByUsername(username);
    }
}
