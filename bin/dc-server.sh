#!/bin/sh

#JAVA_HOME=

DC_MIN_MEM=4g
DC_MAX_MEM=8g


DC_HOME=$(dirname $0)/..

DC_HOME=`cd "$DC_HOME"; pwd`

DC_JAVA_OPTS="$DC_JAVA_OPTS -Ddc.log.file=server.log"
DC_JAVA_OPTS="$DC_JAVA_OPTS -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$DC_HOME/logs/server.dump"

. "$DC_HOME/bin/dc-env.sh"

if [ "x$PROFILE" != "x" ];
then
    PROFILE="$PROFILE,cache,https"
else
    PROFILE="cache,https"
fi

DC_JAVA_OPTS="$DC_JAVA_OPTS -Dspring.profiles.active=$PROFILE"

DC_CLASSPATH="$CLASSPATH"

DC_PIDFILE="$DC_HOME/bin/webserver.pid"

DC_CONSOLEOUT="$DC_HOME/logs/console.out"

while [ $# -gt 0 ]; do
  COMMAND=$1
  case $COMMAND in
    -swagger)
      DC_PIDFILE="$DC_HOME/bin/swagger.pid"
      DC_JAVA_OPTS="$DC_JAVA_OPTS -Dswagger=true"
      shift
      ;;
    *)
      break
      ;;
  esac
done

case `uname` in
    CYGWIN*)
        DC_CLASSPATH=`cygpath -p -w "$DC_CLASSPATH"`
        DC_HOME=`cygpath -p -w "$DC_HOME"`
    ;;
esac

case $1 in
start)
    echo  -n "Starting webserver ... "
    if [ -f "$DC_PIDFILE" ]; then
      if kill -0 `cat "$DC_PIDFILE"` > /dev/null 2>&1; then
         echo $command already running as process `cat "$DC_PIDFILE"`. 
         exit 0
      fi
    fi
    nohup "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
        com.paibo.datacenter.bizws.WebServerLaunch >> "$DC_CONSOLEOUT" 2>&1 < /dev/null &
    if [ $? -eq 0 ]
    then
      if /bin/echo -n $! > "$DC_PIDFILE"
      then
        sleep 1
        echo STARTED
      else
        echo FAILED TO WRITE PID
        exit 1
      fi
    else
      echo SERVER DID NOT START
      exit 1
    fi
    ;;
stop)
    echo -n "Stopping webserver"
    if [ ! -f "$DC_PIDFILE" ]
    then
      echo "no webserver to stop (could not find file $DC_PIDFILE)"
    else
      stopprocess $(cat "$DC_PIDFILE")
      rm "$DC_PIDFILE"
    fi
    exit 0
    ;;
restart)
    shift
    "$0" stop ${@}
    sleep 5
    "$0" start ${@}
    ;;
*)
    echo "Usage: $0 {start|stop|restart} -swagger" >&2

esac
