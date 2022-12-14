package com.knife4j.annotation;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.knife4j.config.Knife4jApiConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * @author lk
 * @date 2022-12-09
 * 在需要使用knife4j的模块启动类上加上此注解，可自动生成属于该模块的基本信息
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@EnableKnife4j
@EnableSwagger2
@Import({Knife4jApiConfiguration.class})
public @interface EnableKnife4jApi {
}
