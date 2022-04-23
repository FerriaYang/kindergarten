package com.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Child;
import com.entity.Course;
import com.mapper.ChildDao;
import com.mapper.CourseDao;
import com.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Damon
 * @since 2022-02-10
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public Page<Course> getAll(String task, Page page) {
        return courseDao.selectAll(task, page);
    }
}
