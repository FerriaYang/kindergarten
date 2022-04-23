package com.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ferria
 * @since 2022-02-17
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 用户名
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 图片
     */
    private String img;

    /**
     * 身份号
     */
    private Integer identityNum;

    /**
     * 家长编号
     */
    private Integer parentId;

    /**
     * 教师编号
     */
    private Integer teacherId;

    /**
     * 状态
     */
    private Integer status;

    public User(Integer username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(Integer username, String password, String nickName, Integer identityNum) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.identityNum = identityNum;
    }
}
