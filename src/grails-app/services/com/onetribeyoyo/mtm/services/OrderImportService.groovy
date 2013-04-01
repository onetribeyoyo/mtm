package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

class OrderImportService extends ImportService {

    boolean transactional = true

    def ProjectService

    static final List<String> REQUIRED_FIELDS = [
        "story",
        "x",
        "y",
        "order",
    ]

    static final List<String> OPTIONAL_FIELDS = []

    void importOrder(Project project, String filename) {
        log.info "importOrder(${filename})"

        File dataFile = new File(filename)
        if (dataFile.exists()) {
            extractData(project, new FileReader(dataFile))
        } else {
            throw new RuntimeException("file:\"${filename}\" does not exist.")
        }

        log.info "importOrder(..) done."
    }

    private def extractData(Project project, Reader file) {
        log.info "  extractData(..)"

        def indexMap = [:]

        file.eachCsvLine { fields -> // using the CSV plugin

            if (!indexMap) {
                // must be the first line, so figure out which fields contain the data we care about...
                indexMap = extractIndexMap(fields, REQUIRED_FIELDS, OPTIONAL_FIELDS)

            } else if (!empty(fields)) { // skip blank lines
                // must be a data line...

                String summary = extractString(fields, indexMap.story)
                Story story = Story.findByProjectAndSummary(project, summary)
                if (!story) {
                    // skip records for unknown stories
                } else {
                    Long order = extractLong(fields, indexMap.order)
                    if (order) {
                        Dimension xAxis = project.dimensionFor(extractString(fields, indexMap.xAxis))
                        Element x = xAxis?.elementFor(extractString(fields, indexMap.x))
                        Dimension yAxis = project.dimensionFor(extractString(fields, indexMap.yAxis))
                        Element y = yAxis?.elementFor(extractString(fields, indexMap.y))
                        OrderedStory ordering = story.orderingFor(x, y)
                        if (!ordering) {
                            ordering = new OrderedStory(story: story, x: x, y: y)
                            story.addToOrdering(ordering)
                        }
                        ordering.order = order
                        if (ordering.validate()) {
                            story.save()
                        } else {
                            println ordering.errors
                            throw new RuntimeException("invalid order data.")
                        }
                    }
                }
            }
        }

        if (!indexMap) {
            throw new RuntimeException("Empty file.")
        }

        log.info "  extractData(..) done."

        project
    }

}
