package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

class StructureImportService extends ImportService {

    boolean transactional = true

    def ProjectService

    static final List<String> REQUIRED_FIELDS = [
        "dimension",
        "value",
    ]

    static final List<String> OPTIONAL_FIELDS = [
        "order",
        "colour",
        "description",
        "primaryAxis",
        "colourDimension",
        "highlightDimension",
    ]

    void importStructure(Project project, String filename) {
        log.info "importStructure(${filename})"

        File dataFile = new File(filename)
        if (dataFile.exists()) {
            extractData(project, new FileReader(dataFile))
        } else {
            throw new RuntimeException("file:\"${filename}\" does not exist.")
        }

        log.info "importStructure(..) done."
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

                String dimensionName = extractString(fields, indexMap.dimension)
                Dimension dimension = project.dimensionFor(dimensionName)
                if (!dimension) {
                    dimension = new Dimension(name: dimensionName)
                    project.addToDimensions(dimension)
                    if (dimension.validate()) {
                        project.save(flush: true)
                    } else {
                        println dimension.errors
                        throw new RuntimeException("invalid element data.")
                    }
                }

                String elementName = extractString(fields, indexMap.value)
                Element element = dimension.elementFor(elementName) ?: new Element(value: elementName)
                element.colour = extractLong(fields, indexMap.colour)
                element.description = extractLong(fields, indexMap.description)
                element.order = extractLong(fields, indexMap.order)

                dimension.addToElements(element)

                if (extractBoolean(fields, indexMap.primaryAxis)) project.primaryAxis = dimension
                if (extractBoolean(fields, indexMap.colourDimension)) project.colourDimension = dimension
                if (extractBoolean(fields, indexMap.highlightDimension)) project.highlightDimension = dimension

                if (element.validate()) {
                    project.save()
                } else {
                    println element.errors
                    throw new RuntimeException("invalid element data.")
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
