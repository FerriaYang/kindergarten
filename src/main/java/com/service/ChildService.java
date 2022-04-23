package com.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Child;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import javax.print.attribute.IntegerSyntax;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Damon
 * @since 2022-02-08
 */
public interface ChildService extends IService<Child> {

    public Page<Child> getChild(String name, Page page);

    public Child getChildById(Integer id);

    public Page<Child> getChildByGradeId(@Param("name") String name, Page page, @Param("id") Integer id);

    public Page<Child> getChildByParentId(@Param("name") String name, Page page, @Param("id") Integer id);

    public List<Child> getChildByParentId(@Param("name") String name, @Param("id") Integer id);
}
