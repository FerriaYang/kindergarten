package com.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Child;
import com.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Damon
 * @since 2022-02-10
 */
@Mapper
public interface CourseDao extends BaseMapper<Course> {

    public Page<Course> selectAll(@Param("task") String task, Page page);
}
