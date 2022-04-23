package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.User;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ferria
 * @since 2022-02-17
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/page")
    public String page(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model, @RequestParam(defaultValue = "-1") Integer username){
        if (username != -1){
            User user = userService.getByUsername(username);
            List<User> list = new ArrayList<>();
            list.add(user);
            model.addAttribute("users", list);
            model.addAttribute("total",1);
            model.addAttribute("current",1);
            model.addAttribute("lastPage",1);
            model.addAttribute("username", username);
        }else {
            if (pageNum < 1){
                pageNum = 1;
            }
            Integer size = 5;
            List<User> list = userService.getAll();
            int total = list.size();
            Integer lastPage = (total + size - 1) / size;
            if (pageNum > lastPage){
                pageNum = lastPage;
            }
            Page<User> page = userService.getAll(new Page<User>(pageNum,size));
            List<User> records = page.getRecords();
            model.addAttribute("users",records);
            model.addAttribute("total",total);
            model.addAttribute("current",page.getCurrent());
            model.addAttribute("lastPage",lastPage);
        }
        return "user";
    }
    
    @ResponseBody
    @RequestMapping("/login")
    public String login(Integer username, String password, HttpSession session){
        User temp = userService.getByUsername(username);
        if (temp == null){
            return "null";
        }
        Integer status = temp.getStatus();
        if (status != null && status == 3){
            return "lock";
        }
        if (password.equals(temp.getPassword())){
            if (status != null){
                temp.setStatus(null);
                userService.editStatusByUsername(null, username);
            }
            session.setAttribute("user", temp);
            return "success";
        }else {
            if (status == null){
                userService.editStatusByUsername(1, username);
            }else {
                userService.editStatusByUsername(status + 1, username);
            }
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping("/uploadImg")
    public boolean uploadImg(@RequestParam("file") MultipartFile img, Integer username, HttpSession session){
        Boolean res = false;
        String imgPath = FastDFSUtil.uploadFile(img);
        if (imgPath != null){
            User user = userService.getByUsername(username);
            String oldImg = user.getImg();
            if (oldImg != null){
                FastDFSUtil.deleteFile(oldImg);
            }
            if (imgPath.length() > 35){
                res = userService.editImgByUsername(imgPath, username);
                user.setImg(imgPath);
                session.setAttribute("user", user);
            }
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/editPassword")
    public boolean editPassword(Integer username, String oldPassword, String newPassword, HttpSession session){
        boolean res = false;
        User user = userService.getByUsername(username);
        if (user.getPassword().equals(oldPassword)){
            res = userService.editPasswordByUsername(newPassword, username);
            user.setPassword(newPassword);
            session.setAttribute("user", user);
        }
        return res;
    }

    @ResponseBody
    @RequestMapping("/editNickName")
    public boolean editNickName(Integer username, String nickName, HttpSession session){
        User user = userService.getByUsername(username);
        boolean res = userService.editNickNameByUsername(nickName, username);
        user.setNickName(nickName);
        session.setAttribute("user", user);
        return res;
    }

    @ResponseBody
    @RequestMapping("/addUser")
    public Integer addUser(String password, String nickName,@RequestParam(required = false, defaultValue = "0") Integer identityNum){
        Integer username;
        User temp;
        do {
            username = new Integer(GenerateRandom.generate(9));
            temp = userService.getByUsername(username);
        }while (temp != null);
        User user = new User(username, password, nickName, identityNum);
        Boolean res = userService.addUser(user);
        if (res){
            return username;
        }
        return -1;
    }

    @RequestMapping("/checkLock")
    public String checkLock(@RequestParam(value = "pageNum" , required = false, defaultValue = "1") Integer pageNum, Model model, @RequestParam(defaultValue = "-1") Integer username){
        if (username != -1){
            User user = userService.getByUsername(username);
            List<User> list = new ArrayList<>();
            list.add(user);
            model.addAttribute("users", list);
            model.addAttribute("total",1);
            model.addAttribute("current",1);
            model.addAttribute("lastPage",1);
            model.addAttribute("username", username);
        }else {
            if (pageNum < 1){
                pageNum = 1;
            }
            Integer size = 5;
            List<User> list = userService.getLock();
            int total = list.size();
            Integer lastPage = (total + size - 1) / size;
            if (pageNum > lastPage){
                pageNum = lastPage;
            }
            Page<User> page = userService.getLock(new Page<User>(pageNum,size));
            List<User> records = page.getRecords();
            model.addAttribute("users",records);
            model.addAttribute("total",total);
            model.addAttribute("current",page.getCurrent());
            model.addAttribute("lastPage",lastPage);
        }
        model.addAttribute("lock", "lock");
        return "user";
    }

    @ResponseBody
    @RequestMapping("/unlock")
    public boolean unlock(Integer username){
        return userService.editStatusByUsername(null, username);
    }

    @ResponseBody
    @RequestMapping("/del")
    public boolean del(Integer username){
        return userService.removeByUsername(username);
    }

    @RequestMapping("/index")
    public String Index(){
        return "index";
    }
}

