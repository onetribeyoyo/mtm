dataSource {
}

// environment specific settings
environments {
    development {
        grails {
            mongo {
                host = "localhost"
                port = 27017
                databaseName = "mtm-dev"
            }
        }
    }
    test {
        grails {
            mongo {
                host = "localhost"
                port = 27017
                databaseName = "mtm-test"
            }
        }
    }
    production {
        grails {
            mongo {
                host = "localhost"
                port = 27017
                databaseName = "mtm-prod"
                // TODO: username = 
                // TODO: password = 
            }
        }
    }
}
