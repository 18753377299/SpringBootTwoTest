package com.example.common.datasource;//package com.example.common.datasource;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 注： 这个类用来设置数据库类别，其中有一个 ThreadLocal 用来保存每个线程的是使用读库，还是写库。
// *
// * Description 这里切换读/写模式
// * 原理是利用ThreadLocal保存当前线程是否处于读模式（通过开始READ_ONLY注解在开始操作前设置模式为读模式，
// * 操作结束后清除该数据，避免内存泄漏，同时也为了后续在该线程进行写操作时任然为读模式
// * @author fxb
// * @date 2018-08-31
// */
//@Slf4j
//public class DbContextHolder {
//
//    public static final String WRITE = "write";
//    public static final String READ = "read";
//
//    private static ThreadLocal<String> contextHolder= new ThreadLocal<>();
//
//    public static void setDbType(String dbType) {
//        if (dbType == null) {
//            log.error("dbType为空");
//            throw new NullPointerException();
//        }
//        log.info("设置dbType为：{}",dbType);
//        contextHolder.set(dbType);
//    }
//
//    public static String getDbType() {
//        return contextHolder.get() == null ? WRITE : contextHolder.get();
//    }
//
//    public static void clearDbType() {
//        contextHolder.remove();
//    }
//}
