package com.onetribeyoyo.mtm.project

import spock.lang.Specification

class DimensionServiceISpec extends Specification {

    def dimensionService
    def projectService

    static final Map X_AXIS_DATA = [ name: "xAxis", elements: ["1", "2"] ]

    def "test updateSortOrder"() {
        given: "a project and a dimension with a few elements"
            Project project = Project.build()
            projectService.configureBasis(project)
            project.save(flush: true)
            def release = project.dimensionFor("release")
            def r1 = release.elementFor("r0.1")
            def r2 = release.elementFor("r0.2")
            def r3 = release.elementFor("r0.3")

        and: "the initial sort order is..."
            r1.order == 0
            r2.order == 1
            r3.order == 2

        when: "sort order is changed"
            //List<Long> sortedIdList = sortOrder.split(",").collect { (it.replace("element-", "")) as Long }
            dimensionService.updateElementOrder(release, [r2.id, r3.id, r1.id])

        then: "the sortOrder of the objects is now specified"
            r1.order == 2
            r2.order == 0
            r3.order == 1
    }

}
