package com.onetribeyoyo.mtm.project

class SiteConfig implements Serializable {

    Date dateCreated
    Date lastUpdated

    String name

    static constraints = {

        lastUpdated nullable:true

        name nullable:false, blank:false, maxSize:255
    }

    String toString(){
       return name
    }
}
