ruleset {
    description "customized from ruleset/grails.xml"

    GrailsDomainHasEquals
    GrailsDomainHasToString
    GrailsDuplicateConstraint
    GrailsDuplicateMapping
    //GrailsPublicControllerMethod
    GrailsServletContextReference
    //GrailsSessionReference   // DEPRECATED
    GrailsStatelessService
}
