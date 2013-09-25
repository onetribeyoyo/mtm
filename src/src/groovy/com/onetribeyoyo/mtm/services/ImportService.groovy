package com.onetribeyoyo.mtm.services

import com.onetribeyoyo.mtm.domain.*

abstract class ImportService {

    protected def extractIndexMap(String[] choices, requiredFields, optionalFields) {

        def allowedFields = requiredFields + optionalFields

        def indexMap = [:]
        choices.eachWithIndex { field, index ->
            String fieldName = trim(field)
            if (fieldName in allowedFields) {
                indexMap[fieldName] = index
            }
        }

        requiredFields.each { field ->
            if (!indexMap.keySet().contains(field)) {
                throw new RuntimeException("Invalid file format: File does not contain a proper header row.  It must at least contain columns for each of ${requiredFields}")
            }
        }

        return indexMap
    }

    protected boolean empty(String[] tokens) {
        return tokens.inject(true) { emptySoFar, token ->
            emptySoFar && !token
        }
    }

    /** trims whitespace as well as pairs of leading and training quotes. */
    protected String trim(String value) {
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

    protected Boolean extractBoolean(fields, index) {
        String str = trim(extractString(fields, index))
        return (str && (str.toLowerCase() != "false"))
    }

    protected Long extractLong(fields, index) {
        String str = trim(extractString(fields, index))
        if (str) {
            try {
                return str as Long
            } catch (NumberFormatException ex) {
                log.error "NumberFormatException: extractLong(${fields}, ${index})"
                return null // TODO: should we be returning null or throwing an exception?
            }
        } else {
            return null
        }
    }

    protected String extractString(fields, index) {
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

}
