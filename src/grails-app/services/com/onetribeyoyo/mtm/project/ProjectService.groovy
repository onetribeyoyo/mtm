package com.onetribeyoyo.mtm.project

class ProjectService {

    static transactional = true

    def dimensionService
    def storyService

    void configureBasis(Project project) {
        log.debug "configureBasis(${project})"
        configureDimensionAndElements(project, DimensionData.RELEASE)
        configureDimensionAndElements(project, DimensionData.STATUS)
    }

    void configureDefaults(Project project) {
        log.debug "configureDefaults(${project})"
        configureBasis(project)
        configureDimensionAndElements(project, DimensionData.ASSIGNED_TO)
        configureDimensionAndElements(project, DimensionData.FEATURE)
        configureDimensionAndElements(project, DimensionData.STRATEGY)
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

        if (params.primaryXAxis && !project.primaryXAxis) project.primaryXAxis = dimension
        if (params.primaryYAxis && !project.primaryYAxis) project.primaryYAxis = dimension

        if (params.colourDimension && !project.colourDimension) project.colourDimension = dimension

        if (params.highlightDimension && !project.highlightDimension) project.highlightDimension = dimension

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

    void delete(Dimension dimension) {
        Project project = dimension.project
        log.debug "delete(${dimension}) from ${project}"

        if (project.dimensions?.size() <= 2) {
            throw new RuntimeException("Project cannot have less than two dimensions.")
        }

        dimension.elements.collect { it }.each { element ->
            delete(element)
            dimension.save(flush: true)
        }

        if (dimension.isPrimaryXAxis()) {
            project.primaryXAxis = project.dimensions.find { other -> (other != dimension) && !other.isPrimaryYAxis() }
        }
        if (dimension.isPrimaryYAxis()) {
            project.primaryYAxis = project.dimensions.find { other -> (other != dimension) && !other.isPrimaryXAxis() }
        }
        if (dimension.isColourDimension()) {
            project.colourDimension = null
        }
        if (dimension.isHighlightDimension()) {
            project.highlightDimension = null
        }

        project.removeFromDimensions(dimension)
        project.save()
        dimension.delete()
    }

    def delete(Element element) {
        Dimension dimension = element.dimension
        log.debug "delete(${element}) from ${dimension}"

        dimension.project.stories.each { story ->
            if (element in story.vector) {
                story.removeFromVector(element)
            }
            OrderedStory.findAllByStory(story).each { order ->
                story.removeFromOrdering(order)
                order.delete()
            }
        }
        dimension.removeFromElements(element)
        element.delete()
    }

    void delete(Project project) {
        log.debug "delete(${project})"

        project.dimensions.each { dimension ->
            dimension.elements.collect { it }.each { element ->
                elementService.delete(element)
                dimension.save(flush: true)
            }
            project.removeFromDimensions(dimension)
            dimension.delete()
        }

        project.stories.each { story ->
            project.removeFromStories(story)
            story.delete()
        }

        project.delete()
    }

    void updateStoryOrder(Project project, Element x, Element y, List<Long> sortedIds) {
        log.debug "updateSortOrder(project:${project.id}, x:${x}, y:${y}, ${sortedIds})"
        sortedIds.eachWithIndex { id, order ->
            Story story = Story.get(id)
            OrderedStory ordering = story.orderingFor(x, y)
            if (!ordering) {
                ordering = new OrderedStory(story:story, x:x, y:y)
                story.addToOrdering(ordering)
            }
            ordering.order = order
            story.save()
        }
    }


    Project createFromJson(String name, def jsonData) {
        log.debug "createFromJson(\"${name}\", jsonData...)"
        // create a new project...
        Project project = new Project(name: name)
        project.save(flush: true, failOnError: true)

        project.estimateUnits = jsonData.estimateUnits
        project.showEstimates = jsonData.showEstimates
        project.save(flush: true, failOnError: true)

        // create the dimensions...
        jsonData.dimensions.each { dimensionData ->
            Dimension dimension = new Dimension(
                name: dimensionData.name,
                description: dimensionData.description,
                colour: dimensionData.colour,
                //layoutStyle: dimensionData.layoutStyle?.toString()
            )
            log.debug "createFromJson(..)     adding dimension: \"${dimension}\""
            project.addToDimensions(dimension)
            project.save(flush: true, failOnError: true)

            dimensionData.elements.each { elementData ->
                Element element = new Element(
                    value: elementData.value,
                    order: elementData.order,
                    colour: elementData.colour,
                    description: elementData.description,
                )
                log.debug "createFromJson(..)         adding element: \"${element}\""
                dimension.addToElements(element)
                dimension.save(flush: true, failOnError: true)
            }
        }

        project.colourDimension = project.dimensionFor(jsonData.colourDimension)
        project.highlightDimension = project.dimensionFor(jsonData.highlightDimension)

        project.primaryXAxis = project.dimensionFor(jsonData.primaryXAxis) ?: project.dimensions.find { dimension -> !dimension.isPrimaryYAxis() }
        project.primaryYAxis = project.dimensionFor(jsonData.primaryYAxis) ?: project.dimensions.find { dimension -> !dimension.isPrimaryXAxis() }

        project.save(flush: true, failOnError: true)

        // create the stories...
        jsonData.stories.each { storyData ->
            Story story = new Story(
                summary: storyData.summary,
                detail: storyData.detail,
                estimate: storyData.estimate,
            )
            log.debug "createFromJson(..)     adding story: \"${story}\""
            project.addToStories(story)
            project.save(flush: true, failOnError: true)

            storyData.vector.each { vectorData ->
                Dimension axis = project.dimensionFor(vectorData.dimension)
                Element position = axis.elementFor(vectorData.value)
                storyService.slide(story, axis, position)
            }
            storyData.ordering.each { orderingData ->
                // TODO: create OrderedStory s
                //ordering = new OrderedStory(story:story, x:x, y:y)
                //story.addToOrdering(ordering)
            }
        }

        return project
    }

}
