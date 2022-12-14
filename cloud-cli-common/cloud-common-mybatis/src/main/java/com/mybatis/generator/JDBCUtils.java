package com.mybatis.generator;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

/**
 * JDBC 工具类
 *
 * @author lk
 * @date 2022-11-09
 */
public class JDBCUtils {

    /**
     * 根据url中的数据库名，获取该数据库下所有的表名字
     *
     * @param url      数据库url
     * @param username 数据库用户名
     * @param password 数据库用户密码
     * @return 该数据库下所有的表名字
     */
    public static List<String> getTablesNameByUrl(String url, String username, String password) {
        //数据库表名
        List<String> tablesName = null;

        try {
            System.out.println(url);
            DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
            System.out.println(dataSource.getUrl());
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String sql = "show tables";
            tablesName = jdbcTemplate.queryForList(sql, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tablesName;
    }
}
