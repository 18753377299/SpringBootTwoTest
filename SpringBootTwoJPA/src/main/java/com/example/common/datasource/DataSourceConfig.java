package com.example.common.datasource;//package com.example.common.datasource;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
////import org.mybatis.spring.SqlSessionFactoryBean;
//
////@Configuration
//public class DataSourceConfig {
//	/**
//     * 写数据源
//     * @Primary 标志这个 Bean 如果在多个同类 Bean 候选时，该 Bean 优先被考虑。
//     * 多数据源配置的时候注意，必须要有一个主数据源，用 @Primary 标志该 Bean
//     */
//    @Primary
//    @Bean
////    @ConfigurationProperties(prefix = "mysql.datasource.write")
//    public DataSource writeDataSource() {
//        return new DruidDataSource();
//    }
//
//    @Bean
////    @ConfigurationProperties(prefix = "mysql.datasource.read")
//    public DataSource read1() {
//    	return  new DruidDataSource();
//    }
//
//    /**
//     * 设置数据源路由，通过该类中的determineCurrentLookupKey决定使用哪个数据源
//     */
//    @Bean
//    public AbstractRoutingDataSource routingDataSource() {
//        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource();
//
//        Map<Object, Object> targetDataSources = new HashMap<>(2);
//
//        targetDataSources.put(DbContextHolder.WRITE, writeDataSource());
//        targetDataSources.put(DbContextHolder.READ, read1());
//        proxy.setDefaultTargetDataSource(writeDataSource());
//        proxy.setTargetDataSources(targetDataSources);
//        return proxy;
//    }
//
//
//    /**
//     * 多数据源需要自己设置sqlSessionFactory
//     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(routingDataSource());
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        // 实体类对应的位置
////        bean.setTypeAliasesPackage(typeAliasesPackage);
//        // mybatis的XML的配置
////        bean.setMapperLocations(resolver.getResources(mapperLocation));
////        bean.setConfigLocation(resolver.getResource(configLocation));
//        return bean.getObject();
//    }
//
//
//
//    /**
//     * 设置事务，事务需要知道当前使用的是哪个数据源才能进行事务处理
//     */
//    @Bean
//    public DataSourceTransactionManager dataSourceTransactionManager() {
//        return new DataSourceTransactionManager(routingDataSource());
//    }
//
//
//
//}
