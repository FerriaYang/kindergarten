package com.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Child;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Damon
 * @since 2022-02-08
 */
@Mapper
public interface ChildDao extends BaseMapper<Child> {

    public Page<Child> selectChild(@Param("name") String name, Page page);

    public Child selectChildById(@Param("id") Integer id);

    public Page<Child> selectChildByGradeId(@Param("name") String name, Page page, @Param("id") Integer id);

    public Page<Child> selectChildByParentId(@Param("name") String name, Page page, @Param("id") Integer id);

    public List<Child> selectChildByParentId(@Param("name") String name, @Param("id") Integer id);
}
