
dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:file:~/data/h2db/mtm-prod;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
}


//dataSource {
//	pooled = true
//	driverClassName = "com.mysql.jdbc.Driver"
//
//    properties {
//        maxActive = 50
//        maxIdle = 25
//        minIdle = 5
//        initialSize = 5
//        maxWait = 5000
//        minEvictableIdleTimeMillis = 1000 * 60 * 30 //30 minutes
//        timeBetweenEvictionRunsMillis = 1000 * 60 * 30 //30 minutes
//        numTestsPerEvictionRun = 3
//        testOnBorrow = true
//        testWhileIdle = false
//        testOnReturn = false
//        validationQuery = "select 1;"
//    }
//    // loggingSql = true
//}
//// environment specific settings
//environments {
//    // see Backback writeboard, "MySQL Setup" - https://123.writeboard.com/cxm65oquy4awdte1
//    development {
//        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
//            url = "jdbc:mysql://localhost/mtm_dev?autoReconnect=true"
//            username = "mtm"
//            password = "whatmeworry"
//        }
//    }
//    test {
//        dataSource {
//            dbCreate = "create-drop" //"create-drop" // one of 'create', 'create-drop','update'
//            url = "jdbc:mysql://localhost/mtm_test?autoReconnect=true"
//            username = "mtm"
//            password = "whatmeworry"
//        }
//    }
//    production {
//        dataSource {
//            dbCreate = "none" //"create-drop" // one of 'create', 'create-drop','update'
//            url = "jdbc:mysql://localhost/mtm_prod?autoReconnect=true"
//            username = "mtm"
//            password = "whatmeworry"
//        }
//    }
//}
