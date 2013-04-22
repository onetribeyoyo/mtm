grails.servlet.version = "2.5" // Change depending on target container compliance (2.5 or 3.0)
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // specify dependency exclusions here; for example, uncomment this to disable ehcache:
        // excludes 'ehcache'
    }
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    checksums true // Whether to verify checksums on resolve
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility

    repositories {
        inherits true // Whether to inherit repository definitions from plugins

        grailsPlugins()
        grailsHome()
        grailsCentral()

        mavenLocal()
        mavenCentral()

        // uncomment these (or add new ones) to enable remote dependency resolution from public Maven repositories
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }

    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes e.g.

        // runtime 'mysql:mysql-connector-java:5.1.22'

        test "org.spockframework:spock-grails-support:0.7-groovy-2.0"
    }

    plugins {
        //~ persistence --------------------------------------------------------------
        runtime ":hibernate:$grailsVersion"
        compile ":mysql-connectorj:5.1.22.1"
        runtime ":database-migration:1.3.2"
        compile ":cache:1.0.1"

        //~ container ----------------------------------------------------------------
        build ":tomcat:$grailsVersion"

        //~ user interface -----------------------------------------------------------
        runtime ":jquery:1.8.3"
        compile ":jquery-ui:1.8.24"
        compile ":famfamfam:1.0.1"

        //~ resources ----------------------------------------------------------------
        runtime ":resources:1.1.6"
        runtime ":gsp-resources:0.4.1" // NOTE: gsp-resources plugin requires groovy be available on the path at server startup time!
        //runtime ":zipped-resources:1.0"
        //runtime ":cached-resources:1.0"
        //runtime ":yui-minify-resources:0.1.5"

        //~ misc ---------------------------------------------------------------------
        compile ":csv:0.3.1"
        compile ":export:1.5"
        compile ":famfamfam:1.0.1"
        //compile ":joda-time:1.4"
        compile ":remote-pagination:0.4.3"

        //~ testing ------------------------------------------------------------------
        test(":spock:0.7") {
            exclude "spock-grails-support"
        }
        compile ":build-test-data:2.0.5"

        //~ code quality -------------------------------------------------------------
        compile ":codenarc:0.18.1"     // generate report with `grails codenarc`
        compile ":gmetrics:0.3.1"    // generate report with `grails gmetrics`
        test ":code-coverage:1.2.6"  // generate report with `grails test-app -coverage`
    }

}

coverage = {
    enabledByDefault = false
    exclusions = [
    ]
}

codenarc.reports = {
    xmlReport("xml") {
        outputFile = "target/test-reports/CodeNarc-Report.xml"
    }
    htmlReport("html") {
        outputFile = "target/test-reports/CodeNarc-Report.html"
    }
}

codenarc.ruleSetFiles = [
    //"all",

    // grails plugin default rulesets...
    "basic",
    "exceptions",
    "grails",
    "imports",
    "unused",

    // additional rulesets...
    //"braces",
    //"concurrency",
    //"convention",
    //"design",
    //"dry",
    //"formatting",
    //"generic",
    //"groovyism",
    //"jdbc",
    //"junit",
    //"logging",
    //"naming",
    //"security",
    //"serialization",
    //"size",
    //"unnecessary",
].collect { "file:codenarc/${it}.groovy" }
/*
codenarc.ruleSetFiles = [
    "rulesets/basic.xml", // one of grails plugin default rulesets
    "rulesets/braces.xml",
    "rulesets/concurrency.xml",
    "rulesets/convention.xml",
    "rulesets/design.xml",
    "rulesets/dry.xml",
    "rulesets/exceptions.xml", // one of grails plugin default rulesets
    "rulesets/formatting.xml",
    "rulesets/generic.xml",
    "rulesets/grails.xml",  // one of grails plugin default rulesets
    "rulesets/groovyism.xml",
    "rulesets/imports.xml", // one of grails plugin default rulesets
    "rulesets/jdbc.xml",
    "rulesets/junit.xml",
    "rulesets/logging.xml",
    "rulesets/naming.xml",
    "rulesets/security.xml",
    "rulesets/serialization.xml",
    "rulesets/size.xml",
    "rulesets/unnecessary.xml",
    "rulesets/unused.xml", // one of grails plugin default rulesets
]
*/
