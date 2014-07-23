package com.onetribeyoyo.security

class Role {

    String id

    String authority

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Role role = (Role) o

        if (id != role.id) return false

        return true
    }

    int hashCode() {
        return id.hashCode()
    }
}
