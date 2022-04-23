package com.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ferria
 * @since 2022-02-17
 */
public interface UserService extends IService<User> {

    public User getByUsername(Integer username);

    public Page<User> getAll(Page page);

    public List<User> getAll();

    public Page<User> getLock(Page page);

    public List<User> getLock();

    public Boolean editStatusByUsername(Integer status, Integer username);

    public Boolean editParentByUsername(Integer parentId, Integer username);

    public Boolean editTeacherByUsername(Integer teacherId, Integer username);

    public Boolean editImgByUsername(String img, Integer username);

    public Boolean editPasswordByUsername(String password, Integer username);

    public Boolean editNickNameByUsername(String nickName, Integer username);

    public Boolean addUser(User user);

    public Boolean removeByUsername(Integer username);
}
