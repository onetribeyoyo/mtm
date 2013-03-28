package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*
import com.onetribeyoyo.mtm.util.LayoutStyle

class ProjectService {

    static transactional = true

    def storyService

    // TODO: rework this so dimension data can be edited at runtime.

    static final Map RELEASE_DIMENSION_DATA = [
        name: "release",
        elements: ["r0.1", "r0.2", "r0.3"],
        colour: null, // no colour means the odd/even stripping will be used
        layoutStyle: LayoutStyle.LINEAR
    ]
    static final Map STATUS_DIMENSION_DATA = [
        name: "status",
        elements: ["on deck":       "Gainsboro",
                   "in progress":   "LightGoldenrod3",
                   "ready to test": "PaleTurquoise",
                   "done":          "DarkSeaGreen1"],
        colour: null, // no colour means the odd/even stripping will be used
        layoutStyle: LayoutStyle.LINEAR
    ]

    static final Map ASSIGNED_TO_DIMENSION_DATA = [
        name: "assigned to",
        elements: ["you", "me", "them", "everybody"],
        colour: "goldenrod",
        layoutStyle: LayoutStyle.FLOW
    ]
    static final Map BUG_DIMENSION_DATA = [
        name: "bugs",
        elements: ["fatal", "critical", "important", "low", "trivial"],
        colour: "goldenrod",
        layoutStyle: LayoutStyle.FLOW
    ]
    static final Map FEATURE_DIMENSION_DATA = [
        name: "feature",
        elements: ["feature 1", "feature 2", "feature 3"],
        colour: "SteelBlue",
        layoutStyle: LayoutStyle.FLOW
    ]
    static final Map STRATEGY_DIMENSION_DATA = [
        name: "strategy",
        elements: ["strengths", "weaknesses", "opportunities", "threats"],
        colour: "SteelBlue",
        layoutStyle: LayoutStyle.FLOW
    ]


    void configureBasis(Project project) {
        log.debug "configureBasis(${project})"

        Dimension primary = configureDimensionAndElements(project, RELEASE_DIMENSION_DATA)
        project.primary = primary

        configureDimensionAndElements(project, STATUS_DIMENSION_DATA)
    }

    void configureDefaults(Project project) {
        log.debug "configureDefaults(${project})"
        configureBasis(project)
        configureDimensionAndElements(project, ASSIGNED_TO_DIMENSION_DATA)
        configureDimensionAndElements(project, FEATURE_DIMENSION_DATA)
        configureDimensionAndElements(project, STRATEGY_DIMENSION_DATA)
    }

    Dimension configureDimensionAndElements(Project project, Map params) {
        Dimension dimension = configureDimension(project, params)
        configureElements(dimension, params.elements)
        return dimension
    }

    /**
     *  Adds a dimension to the project or updates an existing dimension with params.name.
     *  Properties are set from
     *    params.name        (required)
     *    params.colour      (defaults to null)
     *    params.layoutStyle
     */
    Dimension configureDimension(Project project, Map params) {
        log.trace "configureDimension(${project}, ${params})"
        Dimension dimension = project.dimensionFor(params.name)
        if (!dimension) {
            dimension = new Dimension(name: params.name)
            project.addToDimensions(dimension)
        }
        dimension.colour = params.colour
        if (params.layoutStyle) dimension.layoutStyle = params.layoutStyle
        return dimension
    }

    Dimension configureElements(Dimension dimension, Map<String,String> elementData) {
        log.trace "configureElements(${dimension}, ${elementData})"
        configureElements(dimension, elementData.keySet() as List)
        dimension.elements.each { point ->
            point.colour = elementData[point.value]
        }
        return dimension
    }

    void configureElements(Dimension dimension, List<String> elements) {
        log.trace "configureElements(${dimension}, ${elements})"
        elements.eachWithIndex { value, order ->
            Element point = dimension.elementFor(value)
            if (!point) {
                point = new Element(value: value)
                dimension.addToElements(point)
            }
            point.order = order
        }
    }



    Dimension createDimension(Project project, params = [:]) {
        log.debug "createDimension(project: ${project}, params: ${params})"

        def dimension = new Dimension(project: project)
        dimension.name = params.name
        dimension.description = params.description
        dimension.save(failOnError:true)

        project.addToDimensions(dimension)

        return dimension
    }

    Story createStory(Project project, params = [:]) {
        log.debug "createStory(project: ${project}, params: ${params})"

        def story = new Story(project: project)
        story.summary = params.summary
        story.detail = params.detail
        story.estimate = params.estimate as Long
        story.save(failOnError:true)

        storyService.setVector(story, params)
        story.save(failOnError:true)

        project.addToStories(story)

        return story
    }

    void updateStoryOrder(Project project, Element element, List<Long> sortedIds) {
        log.debug "updateSortOrder(project:${project.id}, element:${element}, ${sortedIds})"

        List<OrderedElement> ordering = project.orderedStoriesFor(element)

        def sortMap = [:] // a map from story ID to sort order.
        sortedIds.eachWithIndex { storyId, order -> sortMap[storyId] = order }

        Long next = sortedIds.size() + 1

        ordering?.each { order ->
            Story story = order.story
            Long newSortOrder = sortMap[story.id]
            if (newSortOrder == null) {
                newSortOrder = next++
            }
            log.debug "updateSortOrder(..)    story:${story.id} ${element.dimension}:${element} from ${order.order} to ${newSortOrder}"
            order.order = newSortOrder
            order.save(failOnError: true)
        }
    }

}
