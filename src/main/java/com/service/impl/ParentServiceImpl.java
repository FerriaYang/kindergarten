package com.service.impl;

import com.entity.Parent;
import com.mapper.ParentDao;
import com.service.ParentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ferria
 * @since 2022-02-08
 */
@Service
public class ParentServiceImpl extends ServiceImpl<ParentDao, Parent> implements ParentService {

}
