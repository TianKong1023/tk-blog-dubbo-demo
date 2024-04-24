package com.kk.blog.tasks;

import com.kk.blog.entity.TokenInfo;
import com.kk.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class TokenCleanTask {

    @Resource
    private UserService userService;

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void asyncCleanTokens() {
        cleanupExpiredTokens();
    }

    @Async("scheduledTaskExecutor")
    public void cleanupExpiredTokens() {
        try {
            log.info("定时清理过期Token - 线程：{} 开始执行", Thread.currentThread().getName());
            List<TokenInfo> expiredTokens = userService.findExpiredTokens();
            if(expiredTokens != null && !expiredTokens.isEmpty()) {
                userService.deleteExpiredTokens(expiredTokens);
            }
            log.info("定时清理过期Token - 执行完毕");
        } catch (Exception e) {
            log.error("定期清理过期Token - 执行失败", e);
        }
    }

}
