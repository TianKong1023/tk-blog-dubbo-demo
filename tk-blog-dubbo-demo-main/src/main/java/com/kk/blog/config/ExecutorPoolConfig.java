package com.kk.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
@EnableAsync
@EnableScheduling
public class ExecutorPoolConfig {

    @Value("${thread-pool.scheduledTask.core-pool-size}")
    private Integer scheduledTaskCorePoolSize;

    @Value("${thread-pool.scheduledTask.max-pool-size}")
    private Integer scheduledTaskMaxPoolSize;

    @Value("${thread-pool.scheduledTask.queue-size}")
    private Integer scheduledTaskQueueSize;

    @Value("${thread-pool.scheduledTask.keep-alive-seconds}")
    private Integer scheduledTaskKeepAliveSeconds;

    @Value("${thread-pool.excelTask.core-pool-size}")
    private Integer excelTaskCorePoolSize;

    @Value("${thread-pool.excelTask.max-pool-size}")
    private Integer excelTaskMaxPoolSize;

    @Value("${thread-pool.excelTask.queue-size}")
    private Integer excelTaskQueueSize;

    @Value("${thread-pool.excelTask.keep-alive-seconds}")
    private Integer excelTaskKeepAliveSeconds;

    @Bean("scheduledTaskExecutor")
    public TaskExecutor scheduledTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(scheduledTaskCorePoolSize);
        executor.setMaxPoolSize(scheduledTaskMaxPoolSize);
        executor.setQueueCapacity(scheduledTaskQueueSize);
        executor.setKeepAliveSeconds(scheduledTaskKeepAliveSeconds);
        return executor;
    }

    @Bean("excelTaskExecutor")
    public TaskExecutor excelTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(excelTaskCorePoolSize);
        executor.setMaxPoolSize(excelTaskMaxPoolSize);
        executor.setQueueCapacity(excelTaskQueueSize);
        executor.setKeepAliveSeconds(excelTaskKeepAliveSeconds);
        return executor;
    }
}
