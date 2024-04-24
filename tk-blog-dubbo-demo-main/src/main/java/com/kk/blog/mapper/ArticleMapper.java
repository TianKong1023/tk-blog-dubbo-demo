package com.kk.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kk.blog.entity.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<ArticleInfo> {
    void insertArticle(ArticleInfo article);

    void updateArticle(ArticleInfo articleInfo);
}
