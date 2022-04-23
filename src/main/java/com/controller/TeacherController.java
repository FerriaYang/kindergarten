package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Teacher;
import com.entity.User;
import com.service.TeacherService;
import com.service.UserService;
import com.util.FastDFSUtil;
import com.util.GenerateRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ferria
 * @since 2022-01-06
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @RequestMapping("/page")
    public String page(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model,@RequestParam(defaultValue = "") String name){
        if (pageNum < 1){
            pageNum = 1;
        }
        Integer size = 3;
        QueryWrapper sql = new QueryWrapper();
        if (!name.isEmpty()){
            sql.like("name",name);
        }
        List<Teacher> list = teacherService.list(sql);
        int total = list.size();
        Integer lastPage = (total + size - 1) / size;
        if (pageNum > lastPage){
            pageNum = lastPage;
        }
        Page<Teacher> page = teacherService.page(new Page<Teacher>(pageNum,size),sql);
        List<Teacher> records = page.getRecords();
        model.addAttribute("teachers",records);
        model.addAttribute("total",total);
        model.addAttribute("current",page.getCurrent());
        model.addAttribute("lastPage",lastPage);
        model.addAttribute("name",name);
        return "teacherShow";
    }

    @RequestMapping("/detail")
    public String detail(@RequestParam(value = "id" , required = false, defaultValue = "0") Integer id, Model model,HttpSession session){
        if (id != 0){
            Teacher teacher = teacherService.getById(id);
            model.addAttribute("teacher",teacher);
        }else {
            User user = (User) session.getAttribute("user");
            Teacher teacher = teacherService.getById(user.getTeacherId());
            model.addAttribute("teacher",teacher);
        }
        return "teacherDetail";
    }

    @RequestMapping("/personal")
    public String personal(){
        return "teacherNew";
    }

    @ResponseBody
    @RequestMapping("/list")
    public Map list(String keyWord, Integer pageNum, Integer size){
        Page<Teacher> page = new Page<>(pageNum, size);
        if (keyWord != null && keyWord.length() != 0){
            QueryWrapper<Teacher> sql = new QueryWrapper<>();
            sql.like("name",keyWord);
            page = teacherService.page(page,sql);
        }else{
            page = teacherService.page(page);
        }
        Map map = new HashMap();
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());
        return map;
    }

    @ResponseBody
    @RequestMapping("/del")
    public Boolean remove(Integer id){
        return teacherService.removeById(id);
    }

    @ResponseBody
    @RequestMapping("/save")
    public boolean save(Teacher teacher, @RequestParam("file") MultipartFile img, Integer modify){
        boolean res = false;
        String path = FastDFSUtil.uploadFile(img);
        if (modify == 0){
            if (path != null){
                teacher.setImg(path);
            }
            int id = 0;
            while (id == 0 || teacherService.getById(id) != null){
                id = Integer.parseInt(GenerateRandom.generate(4));
            }
            teacher.setId(id);
            res = teacherService.save(teacher);
        }else if (modify == 1){
            Teacher oldTeacher = teacherService.getById(teacher.getId());
            String oldImg = oldTeacher.getImg();
            if (oldImg != null && path != null){
                FastDFSUtil.deleteFile(oldImg);
                teacher.setImg(path);
            }else if (oldImg == null){
                teacher.setImg(path);
            }
            res = teacherService.updateById(teacher);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/personalAdd")
    public boolean personalAdd(Teacher teacher, @RequestParam("file") MultipartFile img, HttpSession session){
        String path = FastDFSUtil.uploadFile(img);
        if (path != null){
            teacher.setImg(path);
        }
        int id = 0;
        while (id == 0 || teacherService.getById(id) != null){
            id = Integer.parseInt(GenerateRandom.generate(4));
        }
        teacher.setId(id);
        boolean res = teacherService.save(teacher);
        User temp = (User) session.getAttribute("user");
        Integer username = temp.getUsername();
        userService.editTeacherByUsername(id, username);
        User user = userService.getByUsername(username);
        session.setAttribute("user", user);
        return res;
    }

    @ResponseBody
    @RequestMapping("/getById")
    public Map getById(Integer id){
        Map map = new HashMap();
        Teacher teacher = teacherService.getById(id);
        map.put("teacher",teacher);
        return map;
    }
}

