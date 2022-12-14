package com.mybatis.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author lk
 * @date 2022-11-09
 */
@SpringBootTest(classes = MyBatisGenerator.class)
public class MyBatisGenerator {
    @Value("${spring.datasource.url}")
    private final String url = null;
    @Value("${spring.datasource.password}")
    private final String password = null;
    @Value("${spring.datasource.username}")
    private final String username = null;

    /**
     * 处理 all 情况
     */
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

    @Test
    void CodeGenerator() {
        //获取所有表名
        List<String> tablesNameList = JDBCUtils.getTablesNameByUrl(url, username, password);
        //生成代码
        fastCodeGenerator(tablesNameList);

        System.out.println("生成代码成功！");
    }

    /**
     * 根据表名集合生成 数据库下所有表的相关代码
     *
     * @param tablesName 表名集合
     */
    void fastCodeGenerator(List<String> tablesName) {
        FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
                             builder.author("author") // 设置作者
                                    .enableSwagger() // 开启 swagger 模式
                                    .disableOpenDir() //禁止打开输出目录 默认值:true
                                    .commentDate("yyyy-MM-dd")// 注释日期
                                    .dateType(DateType.ONLY_DATE)//定义生成的实体类中日期类型 DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                                    // 指定输出目录 System.getProperty 为当前模块的绝对路径，
                                    // 分布式下，可以将System.getProperty("user.dir")换成指定模块的绝对路径
                                    .outputDir(System.getProperty("user.dir") + "/src/main/java/");
                         }).packageConfig(builder -> {
                             builder.parent("com.cli") // 设置父包名
                                    .controller("controller")//Controller 包名 默认值:controller
                                    .entity("entity")//Entity 包名 默认值:entity
                                    .service("service")//Service 包名 默认值:service
                                    .mapper("mapper")//Mapper 包名 默认值:mapper
                                    .moduleName("system") // 设置父包模块名
                                    // 分布式下，可以将System.getProperty("user.dir")换成指定模块的绝对路径
                                    .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/src/main/resources/mapper")); // 设置mapperXml生成路径
                         }).strategyConfig(builder -> {
                             builder.addInclude(tablesName) // 设置需要生成的表名
                                    //.addTablePrefix("t_", "c_") // 设置过滤表前缀
                                    .serviceBuilder()//service策略配置
                                    .formatServiceFileName("%sService").formatServiceImplFileName("%sServiceImpl").entityBuilder()// 实体类策略配置
                                    //.idType(IdType.ASSIGN_ID)//主键策略  雪花算法自动生成的id
                                    .addTableFills(new Column("gmt_create", FieldFill.INSERT)) // 自动填充配置
                                    .addTableFills(new Property("gmtModified", FieldFill.INSERT_UPDATE)).enableLombok() //开启lombok
                                    .logicDeleteColumnName("deleted")// 说明逻辑删除是哪个字段
                                    .enableTableFieldAnnotation()// 属性加上注解说明
                                    .controllerBuilder() //controller 策略配置
                                    .formatFileName("%sController").enableRestStyle() // 开启RestController注解
                                    .mapperBuilder()// mapper策略配置
                                    .formatMapperFileName("%sMapper").enableMapperAnnotation()//@mapper注解开启
                                    .formatXmlFileName("%sMapper");
                         }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                         .execute();
    }
}
