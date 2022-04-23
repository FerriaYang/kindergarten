package com;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.entity.Announcement;
import com.mapper.AnnouncementDao;
import com.service.AnnouncementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class annucementTest {

    @Autowired
    AnnouncementService announcementService;

    @Autowired(required = false)
    AnnouncementDao announcementDao;
    @Test
    public void testAnnouncement(){
        System.out.println(announcementService);
        List<Announcement> list = announcementService.list();
        for (Announcement ann:list) {
            System.out.println(ann);
        }
    }
    @Test
    public void testAnnouncementDao(){
        List<Announcement> list = announcementDao.selectList(null);
        for (Announcement ann:list) {
            System.out.println(ann);
        }
    }
}
