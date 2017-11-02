#!/bin/sh

#JAVA_HOME=

DC_JAVA_OPTS="$DC_JAVA_OPTS -Ddc.log.file=mapredv2.log -DHADOOP_USER_NAME=hadoop"

DC_HOME=$(dirname $0)/..

DC_HOME=`cd "$DC_HOME"; pwd`

LOG4J_PROP="INFO,LOGFILE"

. "$DC_HOME/bin/dc-env.sh"

if [ "x$PROFILE" != "x" ];
then
    DC_JAVA_OPTS="$DC_JAVA_OPTS -Dspring.profiles.active=$PROFILE"
fi

DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:${DC_HOME}/lib/mapredserver/*:${DC_HOME}/conf"

DC_PIDFILE="$DC_HOME/bin/mapreducev2.pid"

DC_CONSOLEOUT="$DC_HOME/logs/consolev2.out"

case `uname` in
    CYGWIN*)
        DC_CLASSPATH=`cygpath -p -w "$DC_CLASSPATH"`
        DC_HOME=`cygpath -p -w "$DC_HOME"`
    ;;
esac

case $1 in

runmr)

	JAVA_OPTS="$JAVA_OPTS -Ddc.root.logger=INFO,console"
	case $2 in
		store2es)
			if [ $# = 4 ] 
			then
				echo "Starting store2es ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.Store2EsSpringHandler $3 $4
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr store2es          Run Fetch Store2Es Data Mr"
				echo "Args: "
				echo "	args[1]  {full|yyyy-MM-dd date}"
				echo "	args[2]  {rid || vid || mob || mac ||imei || imsi}"
			fi
		;;
		import)
			if [ $# = 4 ] 
			then
				echo "Starting import ..."
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
		
		pwd2es)
			if [ $# = 4 ] 
			then
				echo "Starting pwd2es ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" -DprojectCp=/user/hadoop/oozieflow/conf \
				com.paibo.datacenter.integration.mapreduce.improtpwd.ImportHistVidPwd2EsMapReducer $3 $4
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr pwd2es          Run Fetch Pwd2Es Data Mr"
				echo "Args: "
				echo "	args[0]  {ip}"
				echo "	args[1]  {sid}"
			fi
		;;
		
		macass)
			if [ $# = 3 ] 
			then
				echo "Starting macass ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.MacAssFetchSpringHandler $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr macass          Run Fetch MacAss Data Mr"
				echo "Args: "
				echo " args[0] {yyyy-MM-dd date}"
			fi
		;;
		
		cleanmac)
			if [ $# = 3 ] 
			then
				echo "Starting macclear ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.cleaner.mac.MacCleaner $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr cleanmac          Run Fetch macclear Data Mr"
				echo "Args: "
				echo " args[0] {yyyy-MM-dd date}或{yyyyMMdd}"
			fi
		;;
		
		crtIdx)
			if [ $# = 3 ] 
			then
				echo "Starting maccrtindex ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.cleaner.mac.MacCrtIndex $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr crtIdx          Run Fetch maccrtindex Data Mr"
				echo "Args: "
				echo " args[0] {yyyy-MM-dd date}或{yyyyMMdd}"
			fi
		;;
		
		macstore)
			if [ $# = 3 ] 
			then
				echo "Starting macstore ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.MacStoreFetchSpringHandler $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr macstore          Run Fetch MacStore Data Mr"
				echo "Args: "
				echo "	args[0] {yyyy-MM-dd date}"
			fi
		;;
		
		netbarass)
			if [ $# = 3 ] 
			then
				echo "Starting netbarass ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.NetbarAssFetchSpringHandler $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr netbarass          Run Fetch NetbarAss Data Mr"
				echo "Args: "
				echo "	args[0] {yyyy-MM-dd date}"
			fi
		;;
		
		netbarstore)
			if [ $# = 3 ] 
			then
				echo "Starting netbarstore ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.NetbarStoreFetchSpringHandler $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr netbarstore          Run Fetch NetbarStore Data Mr"
				echo "Args: "
				echo "	args[0] {yyyy-MM-dd date}"
			fi
		;;
		
		wifiass)
			if [ $# = 3 ] 
			then
				echo "Starting wifiass ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.WifiAssFetchSpringHandler $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr wifiass         Run Fetch WifiAss Data Mr"
				echo "Args: "
				echo "	args[0] {yyyy-MM-dd date}"
			fi
		;;
		
		wifistore)
			if [ $# = 3 ] 
			then
				echo "Starting wifistore ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.WifiStoreFetchSpringHandler $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr wifistore          Run Fetch WifiStore Data Mr"
				echo "Args: "
				echo "	args[0] {yyyy-MM-dd date}"
			fi
		;;
		
		cntass)
			if [ $# = 4 ] 
			then
				echo "Starting cntass ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.assproc.hdfs.content.HDFSContentAssProcMapReducer $3 $4
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr cntass         Run Fetch cntAss Data Mr"
				echo "Args: "
				echo "	args[0] {yyyy-MM-dd date}"
				echo "	args[1] {qqchat,chat_group}"
			fi
		;;
		
		wificlear)
			if [ $# = 3 ] 
			then
				echo "Starting wificlear ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.taskschedule.handlers.CleanWifiTraceSpringHandler $3
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr wificlear          Run Wificlear Data Mr"
				echo "Args: "
				echo "	args[0] {yyyy-MM-dd date}"
			fi
		;;
		
		 htymtjob)
            if [ $# = 3 ]
            then
                    echo "Starting htymtjob ..."
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
        
        placed)
			if [ $# = 2 ] 
			then
				echo "Starting PlacedISJob ..."
				"$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.analysis.PlacedISJob 
			else
				echo "Usage: dc-mapred2 <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-mapred2.sh runmr placed          Run Fetch placedIs Job"
			fi
		;;
		
		stoped)
	        if [ $# = 2 ]
	        then
	                echo "Starting PlacedISJob ..."
	                "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
	                com.paibo.datacenter.mapredserver.analysis.StopTmISJob
	        else
	                echo "Usage: dc-mapred2 <command> [<args>]" 
	                echo "Commands:"
	                echo " ./dc-mapred2.sh  runmr placed   Run Fetch stoped Job"
	        fi
        ;;

        mac_job)
	        if [ $# = 3 ]
	        then
	                echo "Starting PlacedISJob ..."
	                "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
	                com.paibo.datacenter.mapredserver.cleaner.mac.MacJobsTemp $3
	        else
	                echo "Usage: dc-mapred2 <command> [<args>]" 
	                echo "Commands:"
	                echo " ./dc-mapred2.sh  runmr mac_job yyyy-MM-dd Run mac_job Job"
	        fi
        ;;
        
        fetchPwd)	       
                echo "Starting fetchPwd ..."
                "$JAVA" $JAVA_OPTS $DC_JAVA_OPTS -cp "$DC_CLASSPATH" \
                com.paibo.datacenter.mapredserver.store2es.pwd.VidStorePwd2EsMapReducer $3
        ;;
          
		
		*)
		echo "Usage: $0 $1 {store2es|import|macass|macstore|netbarass|netbarstore|wifiass|wifistore|wificlear|htymtjob|pwd2es|cleanmac|crtIdx |placed | stoped | mac_job }" >&2
	esac
	;;
*)
    echo "Usage: $0 {runmr}" >&2

esac
