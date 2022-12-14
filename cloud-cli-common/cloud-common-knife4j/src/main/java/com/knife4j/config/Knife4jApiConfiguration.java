package com.knife4j.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.servlet.ServletContext;

/**
 * @author lk
 * @date 2022-12-09
 * knife4j文档的基本信息
 */
public class Knife4jApiConfiguration {

    @Value("${spring.profiles.active}")
    private String env;
    @Value("${spring.application.name}")
    private String serviceName;


    @Bean
    public Docket createDocket(ServletContext servletContext) {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(DeferredResult.class)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(serviceName + "接口文档")
                        .version("1.0")
                        .contact(new Contact("xxx", "", ""))
                        .license("XXX有限公司")
                        .build())
                // 如果为生产环境，则不创建swagger
                .enable(!"real".equals(env));
    }
}
