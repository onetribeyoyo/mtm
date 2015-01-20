package com.onetribeyoyo.util

import grails.test.spock.IntegrationSpec
import spock.lang.Shared

/**
 *  Using grails with mongodb-gorm doesn't support all the nice automatic cleanup we get with relational GORM.
 *  But to avoid test pollution you can use this as a base class for your integration tests.
 *
 *  Example: to drop the entire database when the test is cleaned up:
 *  <pre>
 *  def setupSpec() {
 *      dropDbOnCleanup = true
 *  }
 *  </pre>
 *
 *  Example: to start with an empty collection:
 *  <pre>
 *  def setupSpec() {
 *      mongoUtil.dropCollections(["mycollection"] as Set)
 *  }
 *  </pre>
 *
 *  Example: to drop a collection when the test is cleaned up:
 *  <pre>
 *  def setupSpec() {
 *      // and drop the collection after each test
 *      dropCollectionsOnCleanup << "project"
 *  }
 *  </pre>
 *
 *  Example: to drop any new collections created during the test:
 *  <pre>
 *  def setupSpec() {
 *      dropNewCollectionsOnCleanup = true
 *  }
 *  </pre>
 */
abstract class MongoIntegrationSpec extends IntegrationSpec {

    @Shared
    def mongoUtil

    @Shared
    boolean dropDbOnCleanup = false // when true, the database will be dropped after every feature method is executed.

    @Shared
    boolean dropDbOnCleanupSpec = false // when true, the database will be dropped after the spec is complete.

    @Shared
    Set<String> dropCollectionsOnCleanup = [] // add collection names that you would like dropped after every feature method is executed.

    @Shared
    Set<String> dropCollectionsOnCleanupSpec = [] // add collection names that you would like dropped after the spec is complete.

    @Shared
    boolean dropNewCollectionsOnCleanup = false // when true, any new collections will be dropped after every feature method is executed.

    @Shared
    boolean dropNewCollectionsOnCleanupSpec = false // when true, any new collections will be dropped after the spec is complete.


    @Shared
    private Set<String> preservedCollectionNames = []

    @Shared
    private Set<String> preservedSpecCollectionNames = []


    def setupSpec() { // run before the first feature method
        if (dropNewCollectionsOnCleanup) {
            preservedSpecCollectionNames = mongoUtil.getCollectionNames()
        }
    }

    def setup() { // run before every feature method
        if (dropNewCollectionsOnCleanup) {
            preservedCollectionNames = mongoUtil.getCollectionNames()
        }
    }

    def cleanup() { // run after every feature method
        if (dropDbOnCleanup) {
            println "cleanup()  dropping database"
            mongoUtil.dropDatabase()
        } else {
            if (dropCollectionsOnCleanup) {
                println "cleanup()  dropping collections: ${dropCollectionsOnCleanup}"
                mongoUtil.dropCollections(dropCollectionsOnCleanup)
            } else if (dropNewCollectionsOnCleanup) {
                Set<String> newCollectionNames = mongoUtil.getCollectionNames() - preservedCollectionNames - "system.indexes"
                println "cleanup()  preserving collections: ${preservedCollectionNames}"
                println "cleanup()    dropping collections: ${newCollectionNames}"
                mongoUtil.dropCollections(newCollectionNames)
            }
        }
    }

    def cleanupSpec() { // run after the last feature method
        if (dropDbOnCleanupSpec) {
            println "cleanupSpec()  dropping database"
            mongoUtil.dropDatabase()
        } else {
            if (dropCollectionsOnCleanupSpec) {
                println "cleanupSpec()  dropping collections: ${dropCollectionsOnCleanupSpec}"
                mongoUtil.dropCollections(dropCollectionsOnCleanup)
            } else if (dropNewCollectionsOnCleanupSpec) {
                Set<String> newCollectionNames = mongoUtil.getCollectionNames() - preservedSpecCollectionNames - "system.indexes"
                println "cleanupSpec()  preserving collections: ${preservedSpecCollectionNames}"
                println "cleanupSpec()    dropping collections: ${newCollectionNames}"
                mongoUtil.dropCollections(newCollectionNames)
            }
        }
    }

}
