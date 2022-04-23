package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.*;
import com.entity.Course;
import com.service.ChildService;
import com.service.CourseService;
import com.service.GradeService;
import com.service.TeacherService;
import com.util.DateString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Damon
 * @since 2022-02-10
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    
    @Autowired
    CourseService courseService;
    
    @Autowired
    GradeService gradeService;
    
    @Autowired
    TeacherService teacherService;

    @Autowired
    ChildService childService;

    @RequestMapping("/page")
    public String page(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model, @RequestParam(defaultValue = "") String task){
        if (pageNum < 1){
            pageNum = 1;
        }
        Integer size = 5;
        QueryWrapper sql = new QueryWrapper();
        if (!task.isEmpty()){
            sql.like("task",task);
        }
        List<Course> list = courseService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Course> page = courseService.getAll(task,new Page<Course>(pageNum,size));
        List<Course> records = page.getRecords();
        model.addAttribute("courses",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("task",task);
        return "course";
    }

    @ResponseBody
    @RequestMapping("/save")
    public Boolean save(Integer id, String task, String content, String evaluation, Integer gradeId, Integer teacherId, @RequestParam("dateTime") String dateTime, Integer modify){
        Boolean res = false;
        Course course = new Course(id, task, content, evaluation, gradeId, teacherId);
        String temp = dateTime.replaceAll("T", " ");
        Date time = DateString.strToDate(temp, "yyyy-MM-dd HH:mm");
        course.setTime(time);
        if (modify == 1){
            res = courseService.save(course);
        }else if (modify == 0){
            res = courseService.updateById(course);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/prepare")
    public Map prep(Integer id){
        Map map = new HashMap();
        List<Grade> grades = gradeService.list();
        List<Teacher> teachers = teacherService.list();
        map.put("grades",grades);
        map.put("teachers",teachers);
        if (id != 0){
            Course course = courseService.getById(id);
            map.put("course", course);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/del")
    public Boolean del(Integer id){
        return courseService.removeById(id);
    }

    @RequestMapping("/teacherDetail")
    public String teacherDetail(@RequestParam("teacherId") Integer id, Model model){
        Teacher teacher = teacherService.getById(id);
        model.addAttribute("teacher",teacher);
        return "teacherDetail";
    }

}

