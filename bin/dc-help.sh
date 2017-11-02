#!/bin/sh

#JAVA_HOME=

LOG4J_PROP="DEBUG,console"

DC_JAVA_OPTS="$DC_JAVA_OPTS -Ddc.log.file=console.out"

DC_HOME=$(dirname $0)/..

DC_HOME=`cd "$DC_HOME"; pwd`

. "$DC_HOME/bin/dc-env.sh"

if [ "x$PROFILE" != "x" ];
then
    DC_JAVA_OPTS="$DC_JAVA_OPTS -Dspring.profiles.active=$PROFILE"
fi

DC_CLASSPATH="$CLASSPATH"

case `uname` in
    CYGWIN*)
        DC_CLASSPATH=`cygpath -p -w "$DC_CLASSPATH"`
        DC_HOME=`cygpath -p -w "$DC_HOME"`
    ;;
esac

case $1 in
inites)
    echo  -n "Initialization elasticsearch ... "
    "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
        com.paibo.datacenter.es.ElasticSearchInit "$@"
    ;;
updatees)
    echo  -n "Initialization elasticsearch ... "
    "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
        com.paibo.datacenter.es.ElasticSearchInit "$@"
    ;;
resetes)
    echo  -n "Initialization reset elasticsearch ... "
    "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
        com.paibo.datacenter.es.ClearElasticTemplate "$@"
    ;;
initdb)
    echo  -n "Initialization database ... "
    "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
        com.paibo.datacenter.db.InitializeDatabase "$@"
    ;;
updatedb)
    echo  -n "Initialization update database ... "
    "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
        com.paibo.datacenter.db.InitializeDatabase "$@"
    ;;
inithbase)
	echo  -n "Initialization hbase ... "
	DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:$DC_HOME/lib/mapredserver/*:$DC_HOME/conf"
	"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
		com.paibo.datacenter.mapredserver.mrcommon.init.OozieIntegrationInit $2 $3
	;;
updatestver)
	echo  -n "update hbase ... "
	DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:$DC_HOME/lib/mapredserver/*:$DC_HOME/conf"
	"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
		com.paibo.datacenter.mapredserver.mrcommon.init.OozieIntegrationInit "$@"
	;;   
counter)
	echo  -n "Hbase RowCounter Start ... "
	DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:$DC_HOME/lib/mapredserver/*:$DC_HOME/conf"
	DC_JAVA_OPTS="$DC_JAVA_OPTS -DHADOOP_USER_NAME=hadoop"
	"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
		com.paibo.datacenter.mapredserver.mrcommon.HbaseRowCounterMRUtil $2
	;;

import)
		if [ $# = 3 ] 
		then
			echo "Starting import ..."
			DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:$DC_HOME/lib/mapredserver/*:$DC_HOME/conf"
			DC_JAVA_OPTS="$DC_JAVA_OPTS -DHADOOP_USER_NAME=hadoop"
			"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
			com.paibo.datacenter.integration.mapreduce.db.ImportHistIdStore2HbaseMapReducer $3 $4
		else
			echo "Usage: dc-mapred2 <command> [<args>]" 
			echo "Commands:"
			echo " ./dc-mapred2.sh runmr import          Run Fetch Mac Data Mr"
			echo "Args: "
			echo "	args[0]  {ip}"
			echo "	args[1]  {rid|vid}"
		fi
	 ;;	
 htymtjob)
	    if [ $# = 2 ]
	    then
	            echo "Starting htymtjob ..."
	            DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:$DC_HOME/lib/mapredserver/*:$DC_HOME/conf"
				DC_JAVA_OPTS="$DC_JAVA_OPTS -DHADOOP_USER_NAME=hadoop"
	            "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
	            com.paibo.datacenter.mapredserver.migration.job.HistoryMigrationAssJob $3
	    else
	            echo "Usage: dc-mapred2 <command> [<args>]" 
	            echo "Commands:"
	            echo " ./dc-mapred2.sh runmr htymtjob          Run Fetch HistoryMigrationAssJob Data Mr"
	            echo "Args: "
	            echo "  args[0] {yyyy-MM-dd date}"
	    fi
	 ;;
*)
    echo "Usage: $0 {initdb|updatedb|inites|resetes|inithbase|updatestver|counter|import|htymtjob}" >&2

esac