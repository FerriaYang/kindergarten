package com.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ferria
 * @since 2022-02-17
 */
@Mapper
public interface UserDao extends BaseMapper<User> {

    public User getOneByUserName(@Param("username")Integer username);

    public Page<User> selectAll(Page page);

    public List<User> selectAll();

    public Page<User> selectLock(Page page);

    public List<User> selectLock();

    public Boolean updateStatusByUsername(Integer status, Integer username);

    public Boolean updateParentByUsername(Integer parentId, Integer username);

    public Boolean updateTeacherByUsername(Integer teacherId, Integer username);

    public Boolean updateImgByUsername(String img, Integer username);

    public Boolean updatePasswordByUsername(String password, Integer username);

    public Boolean updateNickNameByUsername(String nickName, Integer username);

    public Boolean insertUser(@Param("user")User user);

    public Boolean deleteByUsername(Integer username);
}
