package com.onetribeyoyo.security

class User {

    transient springSecurityService

    String id

    String username
    String password

    boolean enabled = true
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false

    Date dateCreated
    Date lastUpdated

    static constraints = {
        username blank: false, unique: true
        password blank: false, minSize: 8, validator: validatePassword
    }

    protected static validatePassword = { val, obj ->
        (
            val
            && (val.length() > 7)      // length must be greater than or equal to 8
            //&& (val =~ /[a-z]/)        // must contain one or more uppercase characters
            //&& (val =~ /[A-Z]/)        // must contain one or more lowercase characters
            //&& (val =~ /\p{Digit}/)    // must contain one or more numeric values
            //&& (val =~ /[!@#$%*&+()]/) // must contain one or more special characters
        ) ?: "user.password.weak"
    }

    static mapping = {
        password column: '`password`'
        version false
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeValidate() {
        username = username?.toLowerCase() // ensure username is always lowercase
    }

    def beforeInsert() {
        username = username?.toLowerCase() // ensure username is always lowercase
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty("username")) {
            username = username?.toLowerCase() // ensure username is always lowercase
        }
        if (isDirty("password")) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        // Without a null check for springSecurityService User.build will fail during unit tests!
        password = springSecurityService?.encodePassword(password, username)
    }

    String toString() {
        username
    }

}
