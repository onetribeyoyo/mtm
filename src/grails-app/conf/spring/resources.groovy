import com.onetribeyoyo.util.MongoUtil

// Place your Spring DSL code here
beans = {

    mongoUtil(MongoUtil) {
        it.autowire = true
    }

}
