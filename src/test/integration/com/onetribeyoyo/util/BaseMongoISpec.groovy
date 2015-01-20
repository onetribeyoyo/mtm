package com.onetribeyoyo.util

import com.onetribeyoyo.mtm.project.Project

import spock.lang.Shared

/**
 *  This is basis for the "meta tests" that test the behaviour of the MongoIntegrationSpec itself.
 */
abstract class BaseMongoISpec extends MongoIntegrationSpec {

    // Since we can't guarantee the order of test execution
    // we've got two tests here that should both pass.
    @Shared
    Map lookedFor = [:] // for keeping track of how many times we look for projects.
    @Shared
    Map found     = [:] // for keeping track of how many times we find projects.

    protected void createProject(String name) {
        new Project(name: name).save(flush: true)
    }

    protected void lookForProject(String name) {
        Project project = Project.findByName(name)
        if (!lookedFor[name]) {
            lookedFor[name] = 1
        } else {
            lookedFor[name] = lookedFor[name] + 1
        }

        if (project) {
            if (!found[name]) {
                found[name] = 1
            } else {
                found[name] = found[name] + 1
            }
        }
    }

}
