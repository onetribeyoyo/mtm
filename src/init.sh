#
# mtm specific init
#

#use jdk -a 1.6
#rehome jdk alternate

#use grails -a 2.3.3
#rehome grails alternate
#
#use groovy -a 2.2.0
#rehome groovy alternate

use-jdk 1.7
#export JAVA_HOME=`/usr/libexec/java_home -v 1.7`

#use grails 2.3.8
#use groovy 2.2.2
sdk use grails 2.3.8
sdk use groovy 2.2.2

#use # to echo the settings

export JAVA_OPTS="-Xms512m -Xmx512m"
