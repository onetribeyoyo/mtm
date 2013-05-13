#
# mtm specific init
#

use jdk -a 1.6
rehome jdk alternate

use grails -a 2.2.2
rehome grails alternate

use groovy -a 2.0.6
rehome groovy alternate

use # to echo the settings

export JAVA_OPTS="-Xms512m -Xmx512m"
