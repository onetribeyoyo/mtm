package com.onetribeyoyo.util

import org.apache.log4j.MDC


/**
 *  This set of filters logs controller actions as they are accessed.
 *  MDC is loaded with user/action/request info so the log files can be
 *  "de-interlaced."  Performance measurements of each action and view
 *  are also logged.
 *
 *  Enable trace level logging for this class to see incoming params and
 *  the outgoing model.
 */
class ActivityFilters {

    def springSecurityService

    // use the MDC keys in the pattern layout (%X{key}) to include this info in the logs.
    def MDC_USER_NAME_KEY = "user_name"
    def MDC_USER_RID_KEY = "user_rid"
    def MDC_USER_ACTION_KEY = "user_action"
    def ALL_MDC_KEYS = [MDC_USER_NAME_KEY, MDC_USER_RID_KEY, MDC_USER_ACTION_KEY]

    // Used to "wrap" request IDs so they don't get so large as to be useless in log files.
    def nextRid = 0
    def MAX_RID = 1000 // make this as large or as small as you like.

    def ACTION_START_TIME_KEY = "userActivityFilters_actionStartTime"
    def ACTION_STOP_TIME_KEY = "userActivityFilters_actionStopTime"

    def filters = {

        everything( controller:"*", action:"*" ) {

            before = {
                MDC.put MDC_USER_NAME_KEY, getUsername()
                MDC.put MDC_USER_RID_KEY, getNextRid()
                MDC.put MDC_USER_ACTION_KEY, getAction(controllerName, actionName, params)

                if (log.isInfoEnabled()) {
                    log.info ">>"
                    // capture the start time
                    request.ACTION_START_TIME_KEY = System.currentTimeMillis()
                    if (log.isTraceEnabled()) {
                        for ( p in params ) { log.trace ">>     params: ${p}" }
                    }
                }
            }

            after = { model ->
                if (log.isTraceEnabled()) {
                    log.trace "<< model: ${model?.inspect()}"
                    //for ( p in model ) { log.trace "<<     model element: ${p}" }
                }

                if (log.isInfoEnabled()) {
                    // log the action's processing duration
                    def stop  = System.currentTimeMillis()
                    def start = request.ACTION_START_TIME_KEY
                    def duration = start ? (stop - start) : "unknown"

                    log.info "<< action processed in ${duration} millis"
                    request.ACTION_STOP_TIME_KEY = stop
                }
            }

            afterView = {
                if (log.isInfoEnabled()) {
                    // log the view processing duration
                    if (request.ACTION_STOP_TIME_KEY) {
                        def duration = System.currentTimeMillis() - request.ACTION_STOP_TIME_KEY
                        log.info "<< view processed in ${duration} millis"
                    } else {
                        log.info "<< view processed in unknown millis"
                    }
                }

                removeAllMDCKeys()
            }
        }
    }

    private def getUsername() {
        if (springSecurityService?.isLoggedIn()) {
            return springSecurityService?.principal?.username
        } else {
            return "ANONYMOUS"
        }
    }

    private def getAction(controllerName, actionName, params) {
        def action = "${controllerName ?: (params.controllerName ?: '')}"
        if (actionName) {
            action += "/${actionName}"
        } else if (params.action) {
            action += "/${params.action}"
        }
        if (params.id) {
            action += "/${params.id}"
        }
        return (action ?: "-")
    }

    private def getNextRid() {
        if (MAX_RID <= nextRid) { nextRid = 0 } // make sure the id stays small.
        return this.nextRid++
    }

    private def removeAllMDCKeys() {
        ALL_MDC_KEYS.each { key -> MDC.remove key }
    }

}
