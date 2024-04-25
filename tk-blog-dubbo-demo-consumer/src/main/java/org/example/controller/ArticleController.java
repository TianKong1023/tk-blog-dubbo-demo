package org.example.controller;

import com.kk.blog.services.ArticleRPCService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rpc/article")
@RestController
@ResponseBody
public class ArticleController {

    @DubboReference
    private ArticleRPCService articleRPCService;

    @GetMapping("/test")
    public String testRPC() {
        articleRPCService.testDemo();
        return  "123";
    }
}
