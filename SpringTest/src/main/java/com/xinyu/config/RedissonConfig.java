package com.xinyu.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author jiaoxy
 */
public class RedissonConfig {
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() throws IOException {
        /*Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress("redis://1.14.131.172:6379");*/
        RedissonClient redisson = Redisson.create(
                Config.fromYAML(new ClassPathResource("redisson-single.yml").getInputStream()));
        return redisson;
    }

}
