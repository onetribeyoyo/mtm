package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

class ImportService {

    boolean transactional = true

    def storyService

    static final List<String> REQUIRED_FIELDS = [
        "summary",
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
                indexMap = extractIndexMap(fields, project.dimensions)
            } else if (!empty(fields)) { // skip blank lines
                // must be a data line...
                Long storyId = extractField(fields, indexMap.id) as Long
                String storySummary = extractField(fields, indexMap.summary)
                Story story = findOrCreateStory(project, storyId, storySummary)

                String storyDetail = extractField(fields, indexMap.detail)
                if (storyDetail) {
                    story.detail = storyDetail
                }
                story.save()

                project.dimensions.each { dimension ->
                    String value = extractField(fields, indexMap[dimension.name])
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

    private boolean empty(String[] tokens) {
        boolean empty = true // start by assuming we've got nothing
        def e = tokens.each { token -> token
            if (token) {
                empty = false
            }
        }
        return empty
    }

    private def extractIndexMap(String[] fields, def dimensions) {

        def allowedFields = []
        REQUIRED_FIELDS.each { requiredField ->
            allowedFields << requiredField
        }
        dimensions.each { dimension ->
            allowedFields << dimension.name
        }
        def indexMap = [:]

        fields.eachWithIndex { field, index ->
            String fieldName = trim(field)
            if (fieldName in allowedFields) {
                indexMap[fieldName] = index
            }
        }

        REQUIRED_FIELDS.each { field ->
            if (!indexMap.keySet().contains(field)) {
                throw new RuntimeException("Invalid file format: File does not contain a proper header row.  It must at least contain columns for each of ${REQUIRED_FIELDS}")
            }
        }

        return indexMap
    }

    /** trims whitespace as well as pairs of leading and training quotes. */
    private String trim(String value) {
        if (!value) {
            return value
        } else {
            String trimmedValue = value.trim()

            if (trimmedValue.startsWith("\"") && trimmedValue.endsWith("\"")) {
                trimmedValue = trimmedValue.substring(1, trimmedValue.length() - 1)
            }
            if (trimmedValue.startsWith("\'") && trimmedValue.endsWith("\'")) {
                trimmedValue = trimmedValue.substring(1, trimmedValue.length() - 1)
            }

            trimmedValue = trimmedValue.trim()

            return trimmedValue
        }
    }

    private String extractField(fields, index) {
        if ((index == null) || (index >= fields.size())) {
            return null
        } else {
            String value = trim( fields[index] )
            if (!value) {
                return null
            } else {
                return value
            }
        }
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
