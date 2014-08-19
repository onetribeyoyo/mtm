import com.onetribeyoyo.mtm.project.*
import com.onetribeyoyo.security.*

import grails.util.GrailsUtil
import groovy.json.JsonSlurper

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.GrailsApplication

import org.springframework.core.io.ClassPathResource

class BootStrap {

    def authorizationService
    def storyImportService
    def projectService

    def mongoUtil

    def initDev = {
        def start = System.currentTimeMillis()
        log.info "initDev..."

        initCoreData()
        initSecurity()
        initDevUsers()
        initDevData()
        log.info "...database initialized."

        def finish = System.currentTimeMillis()
        log.debug "initDev() completed in ${(finish - start) / 1000} seconds"
    }

    def initTest = {
        def start = System.currentTimeMillis()
        log.info "initTest..."
        mongoUtil.dropDatabase()
        initCoreData()
        initSecurity()
        log.info "...database initialized."
        def finish = System.currentTimeMillis()
        log.debug "initTest() completed in ${(finish - start) / 1000} seconds"
    }

    def initProd = {
        def start = System.currentTimeMillis()

        log.info "initProd..."

        // Don't re-load the data - it's already been done once!
        initCoreData()
        initSecurity()

        def finish = System.currentTimeMillis()
        log.debug "initProd() completed in ${(finish - start) / 1000} seconds"
    }

    def init = { servletContext ->
        if (!envMapping[GrailsUtil.environment]) {
            return
        } else {
            envMapping[GrailsUtil.environment]()
        }
    }

    def destroy = {
    }

    def envMapping = [
        (GrailsApplication.ENV_TEST):initTest,
        (GrailsApplication.ENV_DEVELOPMENT):initDev,
        (GrailsApplication.ENV_PRODUCTION):initProd
    ]


    //~ Data initializers --------------------------------------------------------------------------

    void initCoreData() {
        log.info "initCoreData..."
    }

    void initSecurity() {
        log.info "initSecurity..."

        // add all necessary roles, etc...
    }

    void initDevUsers() {
        log.info "initDevUsers..."

        ["you", "me", "them", "everybody"].each { username ->
            User user = User.findByUsername(username)
            if (!user) {
                user = new User()
                user.username = username
                user.password = "password"
                user.enabled = true
                user.save(flush: true, failOnError: true)

                authorizationService.authorize(user.id, Project) // so they can create new projects

            }
        }
    }

    void initDevData() {
        log.info "initDevData..."

        //1.times {
        //    loadBootstrapProject("project ${it}")
        //}

        //2.times {
        //    def p = new Project(name: "Project ${it}")
        //    p.save(flush:true, failOnError:true)
        //    if (it % 3) {
        //        projectService.configureBasis(p)
        //    } else {
        //        projectService.configureDefaults(p)
        //    }
        //    p.save(flush:true, failOnError:true)
        //}
    }

    void loadBootstrapProject(String projectName) {
        String filename = ConfigurationHolder.config.mtm.bootstrap.filename
        if (filename) {
            if (new File(filename).exists()) {
                def json = new JsonSlurper().parse(new FileReader(new File(filename)))
                Project project = projectService.createFromJson("Bootstrap Project", json)

                User.list().each { user ->
                    authorizationService.authorize(user.id, project, "OWNER")
                    authorizationService.authorize(user.id, project, "VIEWER")
                }

            } else {
                log.error "Cannot find mtm.bootstrap.filename: '${filename}'."
            }
        }
    }

    void loadBootstrapStories(String projectName) {

        String filename = ConfigurationHolder.config.mtm.bootstrap.filename
        if (filename) {
            if (new File(filename).exists()) {
                def project = new Project(name: "${projectName} for ${filename}")
                project.save(flush:true, failOnError:true)

                //projectService.configureDefaults(project)
                //project.save(flush:true, failOnError:true)

                log.info "loading stories from mtm.bootstrap.filename: '${filename}'..."
                storyImportService.importStories(project, filename)
                project.save(flush:true, failOnError:true)

                def release = project.dimensionFor("release")
                if (release) {
                    release.save(flush:true, failOnError:true)
                } else {
                    release = projectService.configureDimensionAndElements(project, DimensionData.RELEASE)
                }
                project.primaryYAxis = release // TODO: should we always set primaryYAxis?  or just when not included in import data?
                project.save(flush:true, failOnError:true)

                def status = project.dimensionFor("status")
                if (status) {
                    status.save(flush:true, failOnError:true)
                } else {
                    projectService.configureDimensionAndElements(project, DimensionData.STATUS)
                }
                project.primaryXAxis = status // TODO: should we always set primaryXAxis?  or just when not included in import data?
                project.save(flush:true, failOnError:true)
            } else {
                log.error "Cannot find mtm.bootstrap.filename: '${filename}'."
            }
        }
    }

}
