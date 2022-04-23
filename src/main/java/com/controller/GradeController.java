package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Child;
import com.entity.Grade;
import com.entity.Grade;
import com.service.ChildService;
import com.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Damon
 * @since 2022-02-04
 */
@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ChildService childService;

    @RequestMapping("/page")
    public String list(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model, @RequestParam(defaultValue = "") String name){
        if (pageNum < 1){
            pageNum = 1;
        }
        Integer size = 5;
        QueryWrapper sql = new QueryWrapper();
        sql.orderByAsc("rade");
        if (!name.isEmpty()){
            sql.like("name",name);
        }
        List<Grade> list = gradeService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Grade> page = gradeService.page(new Page<Grade>(pageNum,size),sql);
        List<Grade> records = page.getRecords();
        model.addAttribute("grades",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("name",name);
        return "grade";
    }

    @ResponseBody
    @RequestMapping("/save")
    public Boolean save(Grade grade, Integer modify){
        Boolean res = false;
        if (modify == 1){
            res = gradeService.save(grade);
        }else if (modify == 0){
            res = gradeService.updateById(grade);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/query")
    public Grade save(Integer id){
        Grade grade = gradeService.getById(id);
        return grade;
    }

    @ResponseBody
    @RequestMapping("/del")
    public boolean del(Integer id){
        boolean res = gradeService.removeById(id);
        return res;
    }

    @RequestMapping("/pageChildren")
    public String page(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model, @RequestParam(defaultValue = "") String name, Integer id){
        if (pageNum < 1){
            pageNum = 1;
        }
        Integer size = 5;
        QueryWrapper sql = new QueryWrapper();
        if (!name.isEmpty()){
            sql.like("name",name);
        }
        sql.eq("grade_id", id);
        List<Child> list = childService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Child> page = childService.getChildByGradeId(name,new Page<Child>(pageNum,size),id);
        List<Child> records = page.getRecords();
        model.addAttribute("childs",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("name",name);
        model.addAttribute("gradeId",id);
        return "child";
    }

}

