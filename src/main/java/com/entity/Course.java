package com.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Damon
 * @since 2022-02-10
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Course implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 编号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课题
     */
    private String task;

    /**
     * 内容
     */
    private String content;

    /**
     * 评价
     */
    private String evaluation;

    /**
     * 班级编号
     */
    private Integer gradeId;

    /**
     * 教师编号
     */
    private Integer teacherId;

    /**
     * 时间
     */
    private Date time;

    @TableField(value = "gradeName",exist = false)
    private String gradeName;

    @TableField(value = "teacherName",exist = false)
    private String teacherName;

    public Course(Integer id, String task, String content, String evaluation, Integer gradeId, Integer teacherId, Date time) {
        this.id = id;
        this.task = task;
        this.content = content;
        this.evaluation = evaluation;
        this.gradeId = gradeId;
        this.teacherId = teacherId;
        this.time = time;
    }

    public Course(Integer id, String task, String content, String evaluation, Integer gradeId, Integer teacherId) {
        this.id = id;
        this.task = task;
        this.content = content;
        this.evaluation = evaluation;
        this.gradeId = gradeId;
        this.teacherId = teacherId;
    }
}
