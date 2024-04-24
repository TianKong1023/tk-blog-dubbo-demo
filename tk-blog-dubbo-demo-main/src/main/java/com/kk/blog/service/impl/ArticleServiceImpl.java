package com.kk.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kk.blog.annotations.ExcelProcesserAnnotation;
import com.kk.blog.convert.ConvertDTO2Entity;
import com.kk.blog.convert.ConvertEntity2Excel;
import com.kk.blog.entity.ArticleInfo;
import com.kk.blog.entity.CommentInfo;
import com.kk.blog.entity.excel.ArticleExcelInfo;
import com.kk.blog.enums.DeleteStatus;
import com.kk.blog.mapper.ArticleMapper;
import com.kk.blog.dto.request.PublishArticleRequestParam;
import com.kk.blog.mapper.CommentMapper;
import com.kk.blog.service.ArticleService;
import com.kk.blog.utils.ExcelExportUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ExcelProcesserAnnotation
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    public ArticleMapper articleMapper;

    @Resource
    public CommentMapper commentMapper;

    @Override
    public List<ArticleInfo> getArticleInfoPage(int currentPageNum, int size, String type) {
        Page<ArticleInfo> page = new Page<>(currentPageNum, size);
        QueryWrapper<ArticleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        queryWrapper.eq("del_flag", DeleteStatus.DISPLAY.getCode());
        Page<ArticleInfo> articleInfoPage = articleMapper.selectPage(page, queryWrapper);
        return articleInfoPage.getRecords();
    }

    @Override
    public void insertArticle(PublishArticleRequestParam param) {
        ArticleInfo articleInfo = ConvertDTO2Entity.convertPublishArticleParam(param);
        articleInfo.setCreateTime(articleInfo.getUpdateTime()); // 跟插入不一样的逻辑
        articleMapper.insertArticle(articleInfo);
    }

    @Override
    public void updateArticle(PublishArticleRequestParam param) {
        ArticleInfo articleInfo = ConvertDTO2Entity.convertPublishArticleParam(param);
        articleInfo.setId(param.getId());
        articleMapper.updateArticle(articleInfo);
    }

    @Override
    public ArticleInfo getArticleInfoById(int id) {
        return articleMapper.selectById(id);
    }

    @Override
    @Async("excelTaskExecutor")
    public void exportArticle() {
        try {
            log.info("导出文章表格，线程{} 开始导出", Thread.currentThread().getName());
            List<ArticleInfo> articleInfos = articleMapper.selectList(new QueryWrapper<>());
            List<ArticleExcelInfo> articleExcelInfos = articleInfos.stream()
                    .map(ConvertEntity2Excel::convertArticleInfo2Excel)
                    .collect(Collectors.toList());
            ExcelExportUtils.exportToExcel(articleExcelInfos, "文章列表");
            log.info("导出完成");
        } catch (Exception e) {
            log.error("导出失败", e);
        }
    }

    @Override
    @Transactional
    public void deleteArticle(Integer articleId) {
        ArticleInfo articleInfo = articleMapper.selectById(articleId);
        articleInfo.setDelFlag(DeleteStatus.DELETE.getCode());
        articleMapper.updateById(articleInfo);
        LambdaQueryWrapper<CommentInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentInfo::getObjectId, articleId);
        List<CommentInfo> commentInfos = commentMapper.selectList(queryWrapper);
        commentInfos.forEach(s -> {
            s.setDelFlag(DeleteStatus.DELETE.getCode());
            commentMapper.updateById(s);
        });
    }

    /**
     * 同类调用异步方法，会使用原始对象调用，而不是代理对象  https://juejin.cn/post/6844903839800229895
     */
    /*@Async("excelTaskExecutor")
    public void asyncExportExcel() {
        try {
            log.info("导出文章表格，线程{} 开始导出", Thread.currentThread().getName());
            List<ArticleInfo> articleInfos = articleMapper.selectList(new QueryWrapper<>());
            List<ArticleExcelInfo> articleExcelInfos = articleInfos.stream()
                    .map(ConvertEntity2Excel::convertArticleInfo2Excel)
                    .collect(Collectors.toList());
            ExcelExportUtils.exportToExcel(articleExcelInfos, "文章列表");
            log.info("导出完成");
        } catch (Exception e) {
            log.error("导出失败", e);
        }
    }*/

}
