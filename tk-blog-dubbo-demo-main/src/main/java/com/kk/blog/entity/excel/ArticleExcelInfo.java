package com.kk.blog.entity.excel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kk.blog.annotations.ExcelFieldAnnotation;
import com.kk.blog.annotations.ExcelProcesserAnnotation;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ArticleExcelInfo {
    /**
     * 主键
     */
    @ExcelFieldAnnotation("id")
    private String id;

    /**
     * 用户id
     */
    @ExcelFieldAnnotation("用户id")
    private String userId;

    /**
     * 文章标题
     */
    @ExcelFieldAnnotation("标题")
    private String title;

    /**
     * 文章类型（1：博文，2：美文）
     */
    @ExcelFieldAnnotation("文章类型")
    private String type;

    /**
     * 文章标签
     */
    @ExcelFieldAnnotation("标签")
    private String tags;


    /**
     * 文章介绍
     */
    @ExcelFieldAnnotation("文章介绍")
    private String introduction;

    /**
     * 文章访问次数
     */
    @ExcelFieldAnnotation("浏览量")
    private String visitCount;


    /**
     * 创建时间
     */
    @ExcelFieldAnnotation("文章创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @ExcelFieldAnnotation("上一次更新时间")
    private String updateTime;
}
