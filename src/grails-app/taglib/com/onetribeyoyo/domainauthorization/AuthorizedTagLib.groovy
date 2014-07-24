package com.onetribeyoyo.domainauthorization

import org.springframework.security.access.AccessDeniedException

class AuthorizedTagLib {

    static namespace = "auth"

    def authorizationService
    def springSecurityService

    /**
     *  When an instance is provided authorization is checked with a call to
     *
     *      authorizationService.isAuthorized(uid, instance, permissions)
     *
     *  If instance authorization fails and a clazz is provided (or if just the clazz is provided)
     *  authorization is checked with a call to
     *
     *      authorizationService.isAuthorized(uid, clazz, permissions)
     */
    def authorized = { attrs, body ->
        if (checkPermissions(attrs.instance, attrs.clazz, attrs.permission, attrs.uidProperty)) {
            out << body()
        }
    }

    def notAuthorized = { attrs, body ->
        if (!checkPermissions(attrs.instance, attrs.clazz, attrs.permission, attrs.uidProperty)) {
            out << body()
        }
    }

    private boolean checkPermissions(instance, clazz, permission, uidProperty = "id") {

        if (clazz instanceof String) { // it might be the name of a domain class...
            clazz = grailsApplication.getDomainClass(clazz)?.clazz ?:
                    grailsApplication.domainClasses.find { it.clazz.simpleName == clazz }?.clazz ?:
                    clazz
        }

        def user = springSecurityService.currentUser
        def uid = user?."${uidProperty}"
        def authorized = false
        try {
            if (instance) {
                authorized = authorizationService.isAuthorized(uid, instance, permission)
            }
            if (!authorized && clazz) {
                authorized = authorizationService.isAuthorized(uid, clazz, permission)
            }
        } catch (AccessDeniedException denied) { // could be thrown by the service methods that use ACLs
            authorized = false
        }
        return authorized
    }

}
