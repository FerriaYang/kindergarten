package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Child;
import com.entity.Child;
import com.entity.Grade;
import com.entity.Parent;
import com.service.ChildService;
import com.service.GradeService;
import com.service.ParentService;
import com.util.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
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
@RequestMapping("/child")
public class ChildController {
    
    @Autowired
    private ChildService childService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ParentService parentService;

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
        List<Child> list = childService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Child> page = childService.getChild(name,new Page<Child>(pageNum,size));
        List<Child> records = page.getRecords();
        model.addAttribute("childs",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("name",name);
        return "child";
    }

    @ResponseBody
    @RequestMapping("/save")
    public boolean save(Child child, @RequestParam("file") MultipartFile img, Integer modify){
        boolean res = false;
        String path = FastDFSUtil.uploadFile(img);
        if (modify == 1){
            if (path != null){
                child.setImg(path);
            }
            childService.save(child);
        }else if (modify == 0){
            Child oldChild = childService.getById(child.getId());
            String oldImg = oldChild.getImg();
            if (oldImg != null && path != null){
                FastDFSUtil.deleteFile(oldImg);
                child.setImg(path);
            }else if (oldImg == null){
                child.setImg(path);
            }
            res = childService.updateById(child);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/prepare")
    public Map prep(Integer id){
        Map map = new HashMap();
        List<Grade> grades = gradeService.list();
        List<Parent> parents = parentService.list();
        map.put("grades",grades);
        map.put("parents",parents);
        if (id != 0){
            Child child = childService.getChildById(id);
            map.put("child",child);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/del")
    public boolean del(Integer id){
        return childService.removeById(id);
    }

    @RequestMapping("/getParent")
    public String getParent(Integer parentId, Model model){
        Parent parent = parentService.getById(parentId);
        model.addAttribute("parent",parent);
        return "parentDetail";
    }
}

