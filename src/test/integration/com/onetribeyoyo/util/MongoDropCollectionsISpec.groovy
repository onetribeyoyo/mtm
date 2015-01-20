package com.onetribeyoyo.util

/**
 *  This is a "meta test" that tests the behaviour of dropping collections with the MongoIntegrationSpec.
 */
class MongoDropCollectionsISpec extends BaseMongoISpec {

    def setupSpec() {
        // make sure we start with no projects
        mongoUtil.dropCollections(["project"] as Set)
        // and drop the project collection after each test
        dropCollectionsOnCleanup << "project"
    }

    def "create a project, can't find the other"() {
        when:
            createProject("project1")
            lookForProject("project1")
            lookForProject("project2")
        then:
            !dropDbOnCleanup
            dropCollectionsOnCleanup
            !dropNewCollectionsOnCleanup
            (1 <= lookedFor.project1) && (lookedFor.project1 <= 2)
            (1 <= lookedFor.project2) && (lookedFor.project2 <= 2)
            found.project1 <= 1
            found.project2 <= 1
    }

    def "create another project, can't find the other"() {
        when:
            createProject("project2")
            lookForProject("project1")
            lookForProject("project2")
        then:
            !dropDbOnCleanup
            dropCollectionsOnCleanup
            !dropNewCollectionsOnCleanup
            (1 <= lookedFor.project1) && (lookedFor.project1 <= 2)
            (1 <= lookedFor.project2) && (lookedFor.project2 <= 2)
            found.project1 <= 1
            found.project2 <= 1
    }

}
