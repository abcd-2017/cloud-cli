package com.redis.config;

/**
 * redisson 配置文件，自动获取配置文件中的redis的信息
 *
 * @author lk
 * @date 2022-11-08
 */
public class RedissonConfig {

//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redissonClient(RedisProperties redisProperties) {
//        Config config = new Config();
//        config.useSingleServer().setAddress(String.format("redis://%s:%d", redisProperties.getHost(), redisProperties.getPort()));
//        String password = redisProperties.getPassword();
//
//        //密码不为空就需要设置上密码
//        if (password != null && !"".equals(password.trim())) {
//            config.useSingleServer().setPassword(password);
//        }
//        return Redisson.create(config);
//    }
}
