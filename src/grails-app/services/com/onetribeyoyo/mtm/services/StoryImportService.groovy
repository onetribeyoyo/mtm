package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

class StoryImportService extends ImportService {

    boolean transactional = true

    def storyService

    static final List<String> REQUIRED_FIELDS = [
        "summary",
    ]

    static final List<String> OPTIONAL_FIELDS = [
        "detail",
        "estimate",
    ]

    static final Integer SESSION_FLUSH_THRESHOLD = 1000

    void importStories(Project project, String filename) {
        log.info "importStories(${filename})"

        File dataFile = new File(filename)
        if (dataFile.exists()) {
            extractData(project, new FileReader(dataFile))
        } else {
            throw new RuntimeException("file:\"${filename}\" does not exist.")
        }

        log.info "importStories(..) done."
    }

    private def extractData(Project project, Reader file) {
        log.info "  extractData(..)"

        def indexMap = [:]

        file.eachCsvLine { fields -> // using the CSV plugin

            if (!indexMap) {
                // must be the first line, so figure out which fields contain the data we care about...

                def optionalFields = OPTIONAL_FIELDS
                project.dimensions.each { dimension ->
                    optionalFields << dimension.name
                }
                indexMap = extractIndexMap(fields, REQUIRED_FIELDS, optionalFields)

            } else if (!empty(fields)) { // skip blank lines
                // must be a data line...
                Long storyId = extractLong(fields, indexMap.id)
                String storySummary = extractString(fields, indexMap.summary)
                Story story = findOrCreateStory(project, storyId, storySummary)

                String storyDetail = extractString(fields, indexMap.detail)
                if (storyDetail) {
                    story.detail = storyDetail
                }
                Long storyEstimate = extractLong(fields, indexMap.estimate)
                if (storyEstimate != null) {
                    story.estimate = storyEstimate
                }
                story.save()

                project.dimensions.each { dimension ->
                    String value = extractString(fields, indexMap[dimension.name])
                    Element element = dimension.elementFor(value)
                    if (value && !element) { // create missing elements
                        element = new Element(dimension: dimension, value: value)
                        dimension.addToElements(element)
                        element.save(failOnError: true)
                    }
                    storyService.slide(story, dimension, element)
                }

                // TODO: should we check all fields(columns) and create missing dimensions?  (for now we just ignore the unknonw columns.)

                if (story.validate()) {
                    project.save()
                } else {
                    println story.errors
                    throw new RuntimeException("invalid story data.")
                }
            }
        }

        if (!indexMap) {
            throw new RuntimeException("Empty file.")
        }

        log.info "  extractData(..) done."

        project
    }

    private Story findOrCreateStory(Project project, Long id, String summary) {
        log.trace "    findOrCreateStory(.., id:${id}, summary:${summary})"
        assert summary
        Story story = null
        if (id) {
            story = Story.findById(id)
        }
        if (!story) {
            story = Story.findByProjectAndSummary(project, summary)
        }
        if (!story) {
            story = new Story(project: project, summary: summary)
            log.debug "      findOrCreateStory(..): creating new story: ${story}"
            project.addToStories(story)
        }
        return story
    }

}
