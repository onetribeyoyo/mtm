package com.onetribeyoyo.security

import org.apache.commons.lang.builder.HashCodeBuilder

class UserRole implements Serializable {

    String id

    User user
    Role role

    boolean equals(other) {
        if (!(other instanceof UserRole)) {
            return false
        }

        other.user?.id == user?.id &&
            other.role?.id == role?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (user) builder.append(user.id)
        if (role) builder.append(role.id)
        builder.toHashCode()
    }

    static UserRole get(long userId, long roleId) {
        find 'from UserRole where user.id=:userId and role.id=:roleId',
            [userId: userId, roleId: roleId]
    }

    static UserRole create(User user, Role role, boolean flush = false) {
        new UserRole(user: user, role: role).save(flush: flush, insert: true)
    }

    static boolean remove(User user, Role role, boolean flush = false) {
        UserRole instance = UserRole.findByUserAndRole(user, role)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(User user, boolean flush = false) {
        def instances = UserRole.findAllByUser(user)
        instances.each { instance ->
            instance.delete(flush: flush)
        }
    }

    static void removeAll(Role role, boolean flush = false) {
        def instances = UserRole.findAllByRole(role)
        instances.each { instance ->
            instance.delete(flush: flush)
        }
    }

    static mapping = {
        id composite: ['role', 'user']
        version false
    }
}
