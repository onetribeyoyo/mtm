package com.onetribeyoyo.mtm.controllers

import com.onetribeyoyo.mtm.domain.*

class ImportController {

    def orderImportService
    def storyImportService
    def structureImportService

    def storyFile(Long id, String filename) {
        render template: "chooseStoryFile", model: [ id: id, filename: filename ]
    }

    def importStories(Long id) {
        Project project = Project.get(id)
        if (!project) {
            flash.error = "Cannot locate project for id:${id}."
            redirect controller: "project", action: "list"
            return

        } else if (!params.file) {
            flash.error = "Filename is required."
            redirect controller: "project", action: "show", id: project.id, model: params
            return

        } else {
            def multipartFile = request.getFile("file")
            if (!multipartFile || multipartFile.empty) {
                flash.error = "Import file is empty."
                redirect controller: "project", action: "show", id: project.id, model: params

            } else {
                try {
                    String filename = multipartFile.originalFilename
                    def parts = [name: null, extension: null]
                    filename.find(/^([^.]*)$|^(.*?)\.?([^.]*)$/) { full, noExtension, name, extension ->
                        parts.name = name ?: noExtension
                        parts.extension = extension
                    }
                    def savedFile = File.createTempFile("tmp_import_file_${parts.name}.", ".${parts.extension ?: 'csv'}")
                    savedFile.deleteOnExit()
                    filename = savedFile.absolutePath

                    multipartFile.transferTo(savedFile)
                    storyImportService.importStories(project, filename)

                    redirect controller: "project", action: "show", id: project.id

                } catch (RuntimeException ex) {
                    flash.error = ex.message
                    redirect controller: "project", action: "show", id: project.id, model: params
                }
            }
        }
    }

    def structureFile(Long id, String filename) {
        render template: "chooseStructureFile", model: [ id: id, filename: filename ]
    }

    def importStructure(Long id) {
        Project project = Project.get(id)
        if (!project) {
            flash.error = "Cannot locate project for id:${id}."
            redirect controller: "project", action: "list"
            return

        } else if (!params.file) {
            flash.error = "Filename is required."
            redirect controller: "project", action: "show", id: project.id, model: params
            return

        } else {
            def multipartFile = request.getFile("file")
            if (!multipartFile || multipartFile.empty) {
                flash.error = "Import file is empty."
                redirect controller: "project", action: "show", id: project.id, model: params

            } else {
                try {
                    String filename = multipartFile.originalFilename
                    def parts = [name: null, extension: null]
                    filename.find(/^([^.]*)$|^(.*?)\.?([^.]*)$/) { full, noExtension, name, extension ->
                        parts.name = name ?: noExtension
                        parts.extension = extension
                    }
                    def savedFile = File.createTempFile("tmp_import_file_${parts.name}.", ".${parts.extension ?: 'csv'}")
                    savedFile.deleteOnExit()
                    filename = savedFile.absolutePath

                    multipartFile.transferTo(savedFile)
                    structureImportService.importStructure(project, filename)

                    redirect controller: "project", action: "show", id: project.id

                } catch (RuntimeException ex) {
                    flash.error = ex.message
                    redirect controller: "project", action: "show", id: project.id, model: params
                }
            }
        }
    }

    def orderFile(Long id, String filename) {
        render template: "chooseOrderFile", model: [ id: id, filename: filename ]
    }

    def importOrder(Long id) {
        Project project = Project.get(id)
        if (!project) {
            flash.error = "Cannot locate project for id:${id}."
            redirect controller: "project", action: "list"
            return

        } else if (!params.file) {
            flash.error = "Filename is required."
            redirect controller: "project", action: "show", id: project.id, model: params
            return

        } else {
            def multipartFile = request.getFile("file")
            if (!multipartFile || multipartFile.empty) {
                flash.error = "Import file is empty."
                redirect controller: "project", action: "show", id: project.id, model: params

            } else {
                try {
                    String filename = multipartFile.originalFilename
                    def parts = [name: null, extension: null]
                    filename.find(/^([^.]*)$|^(.*?)\.?([^.]*)$/) { full, noExtension, name, extension ->
                        parts.name = name ?: noExtension
                        parts.extension = extension
                    }
                    def savedFile = File.createTempFile("tmp_import_file_${parts.name}.", ".${parts.extension ?: 'csv'}")
                    savedFile.deleteOnExit()
                    filename = savedFile.absolutePath

                    multipartFile.transferTo(savedFile)
                    orderImportService.importOrder(project, filename)

                    redirect controller: "project", action: "show", id: project.id

                } catch (RuntimeException ex) {
                    flash.error = ex.message
                    redirect controller: "project", action: "show", id: project.id, model: params
                }
            }
        }
    }

}
