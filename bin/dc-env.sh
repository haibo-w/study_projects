#!/bin/sh

#JAVA_HOME=

#AREA=

if [ "x$AREA" != "x" ]; then
    PROFILE=$AREA
fi

if [ -x "$JAVA_HOME/bin/java" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=`which java`
fi

if [ ! -x "$JAVA" ]; then
    echo "Could not find any executable java binary. Please install java in your PATH or set JAVA_HOME"
    exit 1
fi

if [ "x$DC_MIN_MEM" = "x" ]; then
    DC_MIN_MEM=2g
fi
if [ "x$DC_MAX_MEM" = "x" ]; then
    DC_MAX_MEM=4g
fi

if [ "x$DC_HEAP_SIZE" != "x" ]; then
    DC_MIN_MEM=$DC_HEAP_SIZE
    DC_MAX_MEM=$DC_HEAP_SIZE
fi

if [ "x${LOG4J_PROP}" = "x" ]
then
    LOG4J_PROP="WARN,LOGFILE"
fi

#JVM
JAVA_OPTS="-server $JAVA_OPTS"
JAVA_OPTS="$JAVA_OPTS -Xms${DC_MIN_MEM} -Xmx${DC_MAX_MEM}"
JAVA_OPTS="$JAVA_OPTS -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"

#PATH
JAVA_OPTS="$JAVA_OPTS -Dpath.home=${DC_HOME}"
JAVA_OPTS="$JAVA_OPTS -Djava.io.tmpdir=${DC_HOME}/temp"

#LOGS
JAVA_OPTS="$JAVA_OPTS -Dlog4j.configuration=file:${DC_HOME}/conf/log4j.properties"
JAVA_OPTS="$JAVA_OPTS -Ddc.log.dir=${DC_HOME}/logs"
JAVA_OPTS="$JAVA_OPTS -Ddc.root.logger=${LOG4J_PROP}"

#DEBUG
#DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n"

if [ "x$GC_OPTS" = "x" ]; then
  GC_OPTS="$GC_OPTS -XX:+UseParNewGC"
  GC_OPTS="$GC_OPTS -XX:+UseConcMarkSweepGC"
  GC_OPTS="$GC_OPTS -XX:CMSInitiatingOccupancyFraction=75"
  GC_OPTS="$GC_OPTS -XX:+UseCMSInitiatingOccupancyOnly"
fi

if [ "x$DEBUG_OPTS" != "x" ]; then
  JAVA_OPTS="$JAVA_OPTS $DEBUG_OPTS"
fi

# GC logging options
if [ -n "$GC_LOG_FILE" ]; then
  JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails"
  JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCTimeStamps"
  JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDateStamps"
  JAVA_OPTS="$JAVA_OPTS -XX:+PrintClassHistogram"
  JAVA_OPTS="$JAVA_OPTS -XX:+PrintTenuringDistribution"
  JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCApplicationStoppedTime"
  JAVA_OPTS="$JAVA_OPTS -Xloggc:$GC_LOG_FILE"
  # Ensure that the directory for the log file exists: the JVM will not create it.
  mkdir -p "`dirname \"$GC_LOG_FILE\"`"
fi

JAVA_OPTS="$JAVA_OPTS $GC_OPTS -XX:+DisableExplicitGC"

# Ensure UTF-8 encoding by default (e.g. filenames)
JAVA_OPTS="$JAVA_OPTS -Dfile.encoding=UTF-8"

# Set base classpath
CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/common/*"

# function
stopprocess(){
 pid=$1
 kill $pid
while [ ! -z $pid ]
   do
     pid=`ps ax | awk '{ print $1 }' | grep -e ${pid}`
     if [ -z $pid ] ; then
       echo ""
       echo "STOPPED"
     else
       printf '.'
       sleep 1s
     fi
 done
}