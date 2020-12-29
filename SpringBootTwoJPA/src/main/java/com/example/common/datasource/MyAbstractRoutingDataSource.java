package com.example.common.datasource;//package com.example.common.datasource;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {
//
////    @Value("${mysql.datasource.num}")
////    private int num;
//
//
//    @Override
//    protected Object determineCurrentLookupKey() {
//        String typeKey = DbContextHolder.getDbType();
//        if (typeKey == DbContextHolder.WRITE) {
//            log.info("使用了写库");
//            return typeKey;
//        }
//        //使用随机数决定使用哪个读库
////        int sum = NumberUtil.getRandom(1, num);
////        log.info("使用了读库{}", sum);
////        return DbContextHolder.READ + sum;
//        return DbContextHolder.READ;
//    }
//}
