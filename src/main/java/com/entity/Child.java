package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ferria
 * @since 2022-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Child implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 编号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 图片
     */
    private String img;

    /**
     * 地址
     */
    private String address;

    /**
     * 班级编号
     */
    private Integer gradeId;

    /**
     * 家长编号
     */
    private Integer parentId;

    @TableField(value = "gradeName", exist = false)
    private String gradeName;

    @TableField(value = "parentName", exist = false)
    private String parentName;

    public Child(Integer id, String name, Integer gender, Integer age, String img, String address, Integer gradeId, Integer parentId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.img = img;
        this.address = address;
        this.gradeId = gradeId;
        this.parentId = parentId;
    }
}
