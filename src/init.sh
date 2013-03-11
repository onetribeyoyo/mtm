#
# mtm specific init
#

use jdk 1.6 grails 2.2.1 groovy 2.0.6

use # to echo the settings

export MTM_DEV="$HOME/dev/mtm/app"


#
# HSQL server management
#
HSQL_DB_NAME=mtmDb
HSQL_DB_FILE=$MTM_DEV/database/$HSQL_DB_NAME
HSQL_PORT=9001
HSQL_URL=jdbc:hsqldb:hsql://localhost:$HSQL_DB_PORT/$HSQL_DB_NAME
HSQL_JAR=$GRAILS_HOME/lib/hsqldb-1.8.0.10.jar

alias hsql-start="java -cp $HSQL_JAR org.hsqldb.Server -database.0 file:$HSQL_DB_FILE -dbname.0 $HSQL_DB_NAME"
#alias hsql-stop="java -cp $HSQL_JAR org.hsqldb.util.ShutdownServer -url $HSQL_URL"

export JAVA_OPTS="-Xms512m -Xmx512m"
