package com.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Damon
 * @since 2022-02-10
 */
public interface CourseService extends IService<Course> {

    public Page<Course> getAll(@Param("task") String task, Page page);
}
