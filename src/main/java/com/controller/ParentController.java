package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.*;
import com.entity.Parent;
import com.service.ChildService;
import com.service.ParentService;
import com.service.UserService;
import com.util.FastDFSUtil;
import com.util.GenerateRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Damon
 * @since 2022-02-08
 */
@Controller
@RequestMapping("/parent")
public class ParentController {
    
    @Autowired
    ParentService parentService;

    @Autowired
    ChildService childService;

    @Autowired
    UserService userService;

    @RequestMapping("/page")
    public String page(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model, @RequestParam(defaultValue = "") String name){
        if (pageNum < 1){
            pageNum = 1;
        }
        Integer size = 3;
        QueryWrapper sql = new QueryWrapper();
        if (!name.isEmpty()){
            sql.like("name",name);
        }
        List<Parent> list = parentService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Parent> page = parentService.page(new Page<Parent>(pageNum,size),sql);
        List<Parent> records = page.getRecords();
        model.addAttribute("parents",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("name",name);
        return "parent";
    }

    @ResponseBody
    @RequestMapping("/save")
    public Boolean save(Parent parent, Integer modify){
        Boolean res = false;
        if (modify == 1){
            int id = 0;
            while (id == 0 || parentService.getById(id) != null){
                id = Integer.parseInt(GenerateRandom.generate(4));
            }
            parent.setId(id);
            res = parentService.save(parent);
        }else if (modify == 0){
            res = parentService.updateById(parent);
        }
        return res;
    }

    @RequestMapping("/personal")
    public String personal(){
        return "parentNew";
    }

    @ResponseBody
    @RequestMapping("/personalAdd")
    public boolean personalAdd(Parent parent, HttpSession session){
        int id = 0;
        while (id == 0 || parentService.getById(id) != null){
            id = Integer.parseInt(GenerateRandom.generate(4));
        }
        parent.setId(id);
        boolean res = parentService.save(parent);
        User user = (User) session.getAttribute("user");
        Integer username = user.getUsername();
        userService.editParentByUsername(id, username);
        user.setParentId(id);
        session.setAttribute("user", user);
        return res;
    }

    @ResponseBody
    @RequestMapping("/query")
    public Parent query(Integer id){
        return parentService.getById(id);
    }

    @RequestMapping("/getById")
    public String getById(@RequestParam(value = "id" , required = false, defaultValue = "0") Integer id, Model model, HttpSession session){
        if (id != 0){
            Parent parent = parentService.getById(id);
            model.addAttribute("parent",parent);
        }else {
            User user = (User) session.getAttribute("user");
            Parent parent = parentService.getById(user.getParentId());
            model.addAttribute("parent",parent);
        }
        return "parentDetail";
    }

    @ResponseBody
    @RequestMapping("/del")
    public Boolean del(Integer id){
        return parentService.removeById(id);
    }

    @RequestMapping("/getChild")
    public String page(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model, @RequestParam(defaultValue = "") String name, Integer id){
        if (pageNum < 1){
            pageNum = 1;
        }
        Integer size = 5;
        QueryWrapper sql = new QueryWrapper();
        if (!name.isEmpty()){
            sql.like("name",name);
        }
        sql.eq("parent_id", id);
        List<Child> list = childService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Child> page = childService.getChildByParentId(name,new Page<Child>(pageNum,size),id);
        List<Child> records = page.getRecords();
        model.addAttribute("childs",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("name",name);
        model.addAttribute("parentId",id);
        return "child";
    }

    @RequestMapping("/parentChild")
    public String parentChild(Model model,HttpSession session){
        User user = (User) session.getAttribute("user");
        Integer id = user.getParentId();
        List<Child> children = childService.getChildByParentId("", id);
        model.addAttribute("children", children);
        return "parentChild";
    }
}

