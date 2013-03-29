import com.onetribeyoyo.mtm.domain.*

import grails.util.GrailsUtil
import groovy.util.slurpersupport.NodeChild

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.commons.GrailsApplication

import org.springframework.core.io.ClassPathResource

class BootStrap {

    def storyImportService
    def projectService

    def initDev = {
        def start = System.currentTimeMillis()
        log.info "initDev..."

        // Don't re-load the data - it's already been done once!
        if (SiteConfig.count() == 0) {
            initCoreData()
            initSecurity()
            initDevUsers()
            initDevData()
            log.info "...database initialized."
        } else {
            log.warn "...database already initialized."
        }

        def finish = System.currentTimeMillis()
        log.debug "initDev() completed in ${(finish - start) / 1000} seconds"
    }

    def initTest = {
        def start = System.currentTimeMillis()
        log.info "initTest..."

        // Don't re-load the data - it's already been done once!
        if (SiteConfig.count() == 0) {
            initCoreData()
            initSecurity()
            log.info "...database initialized."
        }

        def finish = System.currentTimeMillis()
        log.debug "initTest() completed in ${(finish - start) / 1000} seconds"
    }

    def initProd = {
        def start = System.currentTimeMillis()

        log.info "initProd..."

        // Don't re-load the data - it's already been done once!
        if (SiteConfig.count() == 0) {
            initCoreData()
            initSecurity()
            log.info "...database initialized."
        }

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

        SiteConfig config = new SiteConfig(name:"Site Configuration")
        config.save(flush:true, failOnError:true)
    }

    void initSecurity() {
        log.info "initSecurity..."
    }

    void initDevUsers() {
        log.info "initDevUsers..."
    }

    void initDevData() {
        log.info "initDevData..."
        1.times {
            loadBootstrapProject("project ${it}")
        }

        13.times {
            def p = new Project(name: "Project ${it}")
            p.save(flush:true, failOnError:true)
            if (it % 3) {
                projectService.configureBasis(p)
            } else {
                projectService.configureDefaults(p)
            }
            p.save(flush:true, failOnError:true)
        }
    }

    void loadBootstrapProject(String projectName) {

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
                    release = projectService.configureDimensionAndElements(project, projectService.RELEASE_DIMENSION_DATA)
                }
                project.primaryAxis = release
                project.save(flush:true, failOnError:true)

                def status = project.dimensionFor("status")
                if (status) {
                    status.save(flush:true, failOnError:true)
                } else {
                    projectService.configureDimensionAndElements(project, projectService.STATUS_DIMENSION_DATA)
                }
            } else {
                log.error "Cannot find mtm.bootstrap.filename: '${filename}'."
            }
        }
    }

}
