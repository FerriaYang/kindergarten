package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Announcement;
import com.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Damon
 * @since 2021-12-15
 */
@RequestMapping("/announcement")
@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @ResponseBody
    @RequestMapping("/list")
    public Map list(Integer pageNum, Integer size){
        Page<Announcement> page = announcementService.page(new Page<Announcement>(pageNum,size));
        Map map = new HashMap();
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());
        return map;
    }

    @RequestMapping("/listall")
    public String listAll(Model model){
        List<Announcement> list = announcementService.list();
        model.addAttribute("announcements",list);
        return "announcement";
    }

    @RequestMapping("/page")
    public String list(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model,@RequestParam(defaultValue = "") String title){
        if (pageNum < 1){
            pageNum = 1;
        }
        Integer size = 5;
        QueryWrapper sql = new QueryWrapper();
        sql.orderByDesc("id");
        if (!title.isEmpty()){
            sql.like("title",title);
        }
        List<Announcement> list = announcementService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Announcement> page = announcementService.page(new Page<Announcement>(pageNum,size),sql);
        List<Announcement> records = page.getRecords();
        model.addAttribute("announcements",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("title",title);
        return "announcement";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam("id") Integer id, Model model){
        Announcement announcement = announcementService.getById(id);
        model.addAttribute("announcement",announcement);
        return "announcementDetail";
    }

    @ResponseBody
    @RequestMapping("/save")
    public Boolean save(Announcement announcement, Integer modify){
        Boolean res = false;
        if (modify == 1){
            res = announcementService.save(announcement);
        }else if (modify == 0){
            res = announcementService.updateById(announcement);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/query")
    public Announcement query(Integer id){
        Announcement announce = announcementService.getById(id);
        return announce;
    }

    @ResponseBody
    @RequestMapping("/del")
    public Boolean remove(Integer id){
        return announcementService.removeById(id);
    }

}

