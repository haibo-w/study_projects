#!/bin/sh

# ---------------------------------------------------------------------
JAVA_HOME=/opt/tomcat-queryserver/jdk1.6.0_35

#LOG_DIR=/opt/mypro/logs
#if ! [ -d "$LOG_DIR" ]; then
#    mkdir $LOG_DIR -p
#fi

#echo "Using CLASSPATH: $CLASSPATH"
# ---------------------------------------------------------------------
$JAVA_HOME/bin/java  -Djava.ext.dirs=lib/ com.paibo.rzx2local.merge.MergeIndex 
