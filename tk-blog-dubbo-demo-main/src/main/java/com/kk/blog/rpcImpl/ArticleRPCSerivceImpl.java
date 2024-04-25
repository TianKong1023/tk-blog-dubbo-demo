package com.kk.blog.rpcImpl;

import com.kk.blog.services.ArticleRPCService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class ArticleRPCSerivceImpl implements ArticleRPCService {
    @Override
    public void testDemo() {
        System.out.println("hello!");
    }
}
