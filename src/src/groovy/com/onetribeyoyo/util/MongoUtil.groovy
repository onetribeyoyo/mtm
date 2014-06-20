package com.onetribeyoyo.util

import com.gmongo.GMongo

import com.mongodb.DB
import com.mongodb.DBCollection

/**
 *  Utility methods for accessing the mongo server and databases.  Use this without passing in any db params
 *  and you'll get the connection data as configured for the grails runtime environment.
 */
class MongoUtil {

    def grailsApplication

    public GMongo getServer(Map dbParams = [:]) {
        String  host = dbParams.host ?: grailsApplication.config.grails.mongo.host
        Integer port = dbParams.port ?: grailsApplication.config.grails.mongo.port
        log.debug "getServer(${dbParams}) host:${host}, port:${port}"
        new GMongo(host, port)
    }

    public DB getDatabase(GMongo server, Map dbParams = [:]) {
        String databaseName = dbParams.databaseName ?: grailsApplication.config.grails.mongo.databaseName
        log.debug "getDatabase(${dbParams}) ${server.mongo}/${databaseName}"
        return server.getDB(databaseName)
    }



    public void dropDatabase(Map dbParams = [:]) {
        Long begin = System.currentTimeMillis()

        GMongo server = getServer(dbParams)
        DB db = getDatabase(server, dbParams)

        log.warn "dropDatabase(${dbParams}) ${server.mongo}/${db.getName()}"
        db.dropDatabase()
        server.mongo.close()

        Long end = System.currentTimeMillis()
        log.debug "dropDatabase(${dbParams}) ${server.mongo}/${db.getName()} in ${end - begin} millis."
    }



    public Set<String> getCollectionNames(Map dbParams = [:]) {
        GMongo server = getServer(dbParams)
        DB db = getDatabase(server, dbParams)
        Set<String> collectionNames = db.getCollectionNames()
        server.mongo.close()
        return collectionNames
    }

    public void dropCollections(Set<String> collectionNames, Map dbParams = [:]) {
        GMongo server = getServer(dbParams)
        DB db = getDatabase(server, dbParams)
        dropCollections(db, collectionNames)
        server.mongo.close()
    }

    public void dropCollections(DB db, Set<String> collectionNames) {
        collectionNames.each { collectionName ->
            dropCollection(db, collectionName)
        }
    }

    public void dropCollection(DB db, String collectionName) {
        Long begin = System.currentTimeMillis()

        DBCollection collection = db.getCollection(collectionName)
        log.warn "dropCollection(${db.getName()}, ${collectionName})"
        collection.drop()

        Long end = System.currentTimeMillis()
        log.debug "dropCollection(${db.getName()}, ${collectionName}) in ${end - begin} millis.    ${new Date()}"
    }

}
