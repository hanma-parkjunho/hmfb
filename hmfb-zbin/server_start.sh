#!/bin/sh
# -----------------------------------------------------------------------------
#   JAVA_HOME       Must point at your Java Development Kit installation.
#   JAVA_OPTS       (Optional) Java runtime options 
# -----------------------------------------------------------------------------

# Set environment variables
JAVA=/usr/bin/java
SPRING_PROFILE=dev
DB_TYPE=mysql
INSTALL_PATH=/hmfb/git/hmfb

# Set Java options
JAVA_OPTS="-Dfile.encoding=UTF-8 -server -Xms512m -Xmx1024m -XX:NewSize=512m -XX:MaxNewSize=512m -XX:PermSize=512m -XX:MaxPermSize=512m"

set BOOT_JAR_PATH=$1

$JAVA -Dspring.profiles.active=$SPRING_PROFILE -DdbType=$DB_TYPE -DinstallPath=$INSTALL_PATH -XX:+HeapDumpOnOutOfMemoryError -jar $BOOT_JAR_PATH
