package com.kk.blog.controller;

import com.kk.blog.convert.ConvertEntity2DTO;
import com.kk.blog.dto.ArticleInfoDTO;
import com.kk.blog.entity.ArticleInfo;
import com.kk.blog.entity.UserInfo;
import com.kk.blog.enums.UserStatus;
import com.kk.blog.dto.request.PublishArticleRequestParam;
import com.kk.blog.dto.response.BaseResponse;
import com.kk.blog.service.ArticleService;
import com.kk.blog.utils.UserContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/blog/article")
@RestController
@Slf4j
public class ArticleController {

    @Resource
    public ArticleService articleService;

    /**
     * 查询文章页
     * @param current
     * @param size
     * @param type
     * @return
     */
    @GetMapping("/fetchData")
    public BaseResponse<List<ArticleInfoDTO>> getArticleInfos(@RequestParam("current") int current, @RequestParam("size") int size, @RequestParam("type") String type) {
        try {
            log.info("查询文章页，请求参数：current = {}, size = {}, type = {}", current, size, type);
            List<ArticleInfo> articleInfoPage = articleService.getArticleInfoPage(current, size, type);
            List<ArticleInfoDTO> articleInfoDTOs = articleInfoPage.stream()
                    .map(ConvertEntity2DTO::convertArticleEntity2DTO)
                    .collect(Collectors.toList());
            BaseResponse<List<ArticleInfoDTO>> baseResponse = BaseResponse.<List<ArticleInfoDTO>>builder()
                    .code(0)
                    .data(articleInfoDTOs)
                    .build();
            return baseResponse;
        } catch (Exception e) {
            log.error("查询文章页失败!", e);
            return BaseResponse.<List<ArticleInfoDTO>>builder()
                    .code(1)
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/{id}")
    public BaseResponse<ArticleInfoDTO> getArticleInfoById(@PathVariable("id") Integer id) {
        try {
            log.info("根据id查询文章详情，id={}", id);
            UserInfo userInfo = UserContext.getUser();
            if(!(userInfo.getStatus().equals(UserStatus.ADMIN.getCode()) || userInfo.getStatus().equals(UserStatus.ROOT.getCode()))) {
                return BaseResponse.<ArticleInfoDTO>builder()
                        .data(null)
                        .code(1)
                        .build();
            }
            ArticleInfo articleInfo = articleService.getArticleInfoById(id);
            ArticleInfoDTO articleInfoDTO = ConvertEntity2DTO.convertArticleEntity2DTO(articleInfo);
            return BaseResponse.<ArticleInfoDTO>builder()
                    .data(articleInfoDTO)
                    .code(0)
                    .build();
        } catch (Exception e) {
            log.error("根据id查询文章详情失败", e);
            return BaseResponse.<ArticleInfoDTO>builder()
                    .code(1)
                    .data(null)
                    .build();
        }
    }

    /**
     * 发布文章
     * @param param
     * @return
     */
    @PostMapping("/publishArticle")
    public BaseResponse<String> publishArticle(@RequestBody PublishArticleRequestParam param) {
        try {
            log.info("发布文章");
            UserInfo userInfo = UserContext.getUser();
            if(!(userInfo.getStatus().equals(UserStatus.ADMIN.getCode()) || userInfo.getStatus().equals(UserStatus.ROOT.getCode()))) {
                return BaseResponse.<String>builder()
                        .data("用户权限不足")
                        .code(1)
                        .build();
            }
            articleService.insertArticle(param);
            return BaseResponse.<String>builder()
                    .code(0)
                    .data("操作成功")
                    .build();
        } catch (Exception e) {
            log.error("发布文章失败", e);
            return BaseResponse.<String>builder()
                    .code(1)
                    .data("操作失败，请稍后重试")
                    .build();
        }
    }

    /**
     * 更新文章
     * @param param
     * @return
     */
    @PostMapping("/updateArticle")
    public BaseResponse<String> updateArticle(@RequestBody PublishArticleRequestParam param) {
        try {
            log.info("更新文章");
            UserInfo userInfo = UserContext.getUser();
            if(!(userInfo.getStatus().equals(UserStatus.ADMIN.getCode()) || userInfo.getStatus().equals(UserStatus.ROOT.getCode()))) {
                return BaseResponse.<String>builder()
                        .data("用户权限不足")
                        .code(1)
                        .build();
            }
            articleService.updateArticle(param);
            return BaseResponse.<String>builder()
                    .code(0)
                    .data("操作成功")
                    .build();
        } catch (Exception e) {
            log.error("更新文章失败", e);
            return BaseResponse.<String>builder()
                    .code(1)
                    .data("操作失败，请稍后重试")
                    .build();
        }
    }

    @GetMapping("/exportArticleExcel")
    public BaseResponse<String> exportArticleExcel() {
        try {
            log.info("导出文章Excel表格");
            UserInfo userInfo = UserContext.getUser();
            if(!(userInfo.getStatus().equals(UserStatus.ADMIN.getCode()) || userInfo.getStatus().equals(UserStatus.ROOT.getCode()))) {
                return BaseResponse.<String>builder()
                        .data("用户权限不足")
                        .code(1)
                        .build();
            }
            articleService.exportArticle();
            return BaseResponse.<String>builder()
                    .data("导出成功")
                    .code(0)
                    .build();
        } catch (Exception e) {
            log.error("导出失败!", e);
            return BaseResponse.<String>builder()
                    .code(1)
                    .data("导出失败！")
                    .build();
        }
    }
    @PostMapping("/deleteArticle")
    public BaseResponse<String> deleteArticle(@RequestParam("articleId") Integer articleId) {
        try {
            log.info("删除文章，id={}", articleId);
            UserInfo userInfo = UserContext.getUser();
            if(!(userInfo.getStatus().equals(UserStatus.ADMIN.getCode()) || userInfo.getStatus().equals(UserStatus.ROOT.getCode()))) {
                return BaseResponse.<String>builder()
                        .data("用户权限不足")
                        .code(1)
                        .build();
            }
            articleService.deleteArticle(articleId);
            return BaseResponse.<String>builder()
                    .data("删除成功")
                    .code(0)
                    .build();
        } catch (Exception e) {
            log.error("删除失败!", e);
            return BaseResponse.<String>builder()
                    .data("系统异常，请稍后再试！")
                    .code(1)
                    .build();
        }
    }

}
