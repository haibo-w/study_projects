#!/bin/sh

#JAVA_HOME=

DC_HOME=$(dirname $0)/..

DC_HOME=`cd "$DC_HOME"; pwd`

. "$DC_HOME/bin/dc-env.sh"

HADOOP_HOME=

OOZIE_HOME=

#hdfs://ns1
nameNode=

#hdnn1:8032
jobTracker=

#format yyyy-MM-dd
startDt=yyyy-MM-dd

es_host=

zookeeper=

if [ "x$HADOOP_HOME" = "x" -o "x$nameNode" = "x" -o "x$jobTracker" = "x" ]; then
	echo "HADOOP setting is not completed"
	exit 1;
fi

if [ "x$startDt" = "xyyyy-MM-dd" -o "x$startDt" = "x" ]; then
	echo "startDt time is not set "
	exit 1;
fi

if [ "x$es_host" = "x" ]; then
	echo "es_host is not set"
	exit 1;
fi

if [ "x$zookeeper" = "x" ]; then
	echo "zookeeper is not set"
	exit 1;
fi

if [ "x$OOZIE_HOME" = "x" ]; then
	echo "OOZIE_HOME is not set"
	exit 1;
fi

export HADOOP_USER_NAME=hadoop

#$HADOOP_HOME/bin/hdfs dfs -ls /

flowPath=/user/hadoop/oozieflow

case $1 in
	
init)

	exFp=`$HADOOP_HOME/bin/hdfs dfs -stat %n $flowPath`
	
	if [ "x$exFp" = "xoozieflow" ];then
		echo "已经初始化过,程序退出";
		exit 1; 
	fi
	
	#mkdir
	$HADOOP_HOME/bin/hdfs dfs -mkdir -p $flowPath
	echo "mkdir $flowPath"
	$HADOOP_HOME/bin/hdfs dfs -mkdir -p $flowPath/apps
	echo "mkdir $flowPath/apps"
	$HADOOP_HOME/bin/hdfs dfs -mkdir -p $flowPath/output
	echo "mkdir $flowPath/output"
	
	#upload dependency
	$HADOOP_HOME/bin/hdfs dfs -put $DC_HOME/conf $flowPath
	$HADOOP_HOME/bin/hdfs dfs -put $DC_HOME/lib $flowPath
	echo "upload dependency lib OK"
	
	#upload workflow
	$HADOOP_HOME/bin/hdfs dfs -put $DC_HOME/conf/oozieflow/* $flowPath/apps
	echo "upload workflow resource OK"
	;;
resetwf)
	
	exFp=`$HADOOP_HOME/bin/hdfs dfs -stat %n $flowPath`
	
	if [ "x$exFp" = "xoozieflow" ];then
		$HADOOP_HOME/bin/hdfs dfs -mv $flowPath $flowPath"_"`date +%Y%m%d%H%M%S`
	fi	
	;;	
		
update)
	
	echo "**************************************************"
	echo "警告：执行前请确保 nameNode、jobTracker、startDt都修改完成"
	echo "**************************************************"
	
	#	dir=`$HADOOP_HOME/bin/hdfs dfs -ls $flowPath`
	#if [ "x$HADOOP_HOME" = "x" ]; then
        #	echo "HADOOP_HOME is not set"
	#        exit 1;
	#fi
	backupDate=`date +%Y%m%d%H%M%S`
	backupDir="$flowPath/backup_hist/backup_$backupDate"	
	$HADOOP_HOME/bin/hdfs dfs -mkdir -p $backupDir
	
	$HADOOP_HOME/bin/hdfs dfs -mv $flowPath/apps $backupDir
	$HADOOP_HOME/bin/hdfs dfs -mv $flowPath/conf $backupDir
	$HADOOP_HOME/bin/hdfs dfs -mv $flowPath/lib $backupDir

	#upload dependency
	$HADOOP_HOME/bin/hdfs dfs -put $DC_HOME/conf $flowPath
	$HADOOP_HOME/bin/hdfs dfs -put $DC_HOME/lib $flowPath
	echo "upload dependency lib OK"
	
	#upload workflow
	$HADOOP_HOME/bin/hdfs dfs -mkdir -p $flowPath/apps
	$HADOOP_HOME/bin/hdfs dfs -put $DC_HOME/conf/oozieflow/* $flowPath/apps

	echo "upload workflow resource OK"
	;;
submit)
	echo "**************************************************"
	echo "警告：执行前请确保 nameNode、jobTracker、startDt都修改完成"
	echo "警告：执行次命令前请确认已经执行过init或update命令"
	echo "警告：此命令只可以执行一次,请确认Oozie是否已经存在相关任务"
	echo "**************************************************"
	
	read -p "请输入oozie IP: " ip
	read -p "请输入oozie 测试方式[dryrun|submit]: " type
	for f in $DC_HOME/conf/oozieflow/* ; do
        #echo $f
        app=${f##*/};
        	
        if [ "x$type" = "xdryrun" ] ; then
        	echo "*************dryrun $f***************"
        $OOZIE_HOME/bin/oozie job -oozie http://$ip:11000/oozie -dryrun -config $DC_HOME/conf/oozieflow/$app/job.properties -DnameNode=${nameNode} -DjobTracker=${jobTracker} -DstartDt=${startDt}T00:00+0800  -Des_host=${es_host} -Dzookeeper=${zookeeper}
		elif [ "x$type" = "xsubmit" ] ; then
				
				if [ "x$app" = "xenvtest" -o "x$app" = "xmacappear" -o "x$app" = "xplacedis" -o "x$app" = "xstoptmis" ] ;then
					continue
				fi
				
				read -p "确认提交$app吗？[确认y|取消n]: " bool
				if [ "x$bool" = "xy" ] ; then
					#echo "确认"
	                id=`$OOZIE_HOME/bin/oozie job -oozie http://$ip:11000/oozie -config $DC_HOME/conf/oozieflow/$app/job.properties -DnameNode=${nameNode} -DjobTracker=${jobTracker} -DstartDt=${startDt}T00:00+0800 -Des_host=${es_host} -Dzookeeper=${zookeeper} -submit`;
	                echo "$app:$id"
					#echo $type;
				elif [ "x$bool" = "xn" ] ; then
					echo "$OOZIE_HOME/bin/oozie job -oozie http://$ip:11000/oozie -config $DC_HOME/conf/oozieflow/$app/job.properties -DnameNode=${nameNode} -DjobTracker=${jobTracker} -DstartDt=${startDt}T00:00+0800 -Des_host=${es_host} -Dzookeeper=${zookeeper} -submit"
				fi
		else
			echo $app;
        fi
    done	

	;;	
submitplus)
	echo "**************************************************"
	echo "警告：执行前请确保 nameNode、jobTracker、startDt都修改完成"
	echo "警告：执行次命令前请确认已经执行过init或update命令"
	echo "警告：此命令只可以执行一次,请确认Oozie是否已经存在相关任务"
	echo "**************************************************"
	
	if [ "x$2" = "x" ]; then
		echo "请输入需要提交的job名称"
		exit 1;
	fi
	
	startDt=`date -d tomorrow "+%Y-%m-%d"`
	
	if [ ! -d "$DC_HOME/conf/oozieflow/$2" ]; then  
	　　echo "$2目录不存在"  
	else
		read -p "请输入oozie IP: " ip
		read -p "请输入oozie 测试方式[dryrun|submit]: " type
		 if [ "x$type" = "xdryrun" ] ; then
        	echo "*************dryrun $f***************"
        	$OOZIE_HOME/bin/oozie job -oozie http://$ip:11000/oozie -dryrun -config $DC_HOME/conf/oozieflow/$2/job.properties  -DnameNode=${nameNode} -DjobTracker=${jobTracker} -DstartDt=${startDt}T00:00+0800 -Des_host=${es_host} -Dzookeeper=${zookeeper};
         elif [ "x$type" = "xsubmit" ] ; then
        	
			id=`$OOZIE_HOME/bin/oozie job -oozie http://$ip:11000/oozie -config $DC_HOME/conf/oozieflow/$2/job.properties -DnameNode=${nameNode} -DjobTracker=${jobTracker} -DstartDt=${startDt}T00:00+0800 -Des_host=${es_host} -Dzookeeper=${zookeeper} -submit`;
        	echo "$2:$id" 
         else
			echo "输入不对";
		 fi	
	fi 
	;;
	
runTest)
	echo "**************************************************"
	echo "警告：执行前请确保 nameNode、jobTracker、startDt都修改完成"
	echo "警告：执行次命令前请确认已经执行过init或update命令"
	echo "**************************************************"
	
	if [ ! -d "$DC_HOME/conf/oozieflow/envtest" ]; then  
	　　echo "envtest目录不存在"  
	else
		read -p "请输入oozie IP: " ip        	
		id=`$OOZIE_HOME/bin/oozie job -oozie http://$ip:11000/oozie -config $DC_HOME/conf/oozieflow/envtest/job.properties -DnameNode=${nameNode} -DjobTracker=${jobTracker} -Des_host=${es_host} -Dzookeeper=${zookeeper} -run`;
		echo "test_env:$id" 
	fi 
	;;
	
rerunwf)
	if [ $# = 4 ] 
	then 
		DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:${DC_HOME}/lib/mapredserver/*:${DC_HOME}/conf"
		echo "start restartwf..."
				"$JAVA" $JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.workflow.util.OozieReRunUtil wf $2 $3 $4
			else
				echo "Usage: dc-workflow <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-workflow.sh rerunwf   Run Fetch rerunwf workflow task"
				echo "Args: "
				echo "  args[1]  {ip}"
				echo "	args[2]  {jobId}"
				echo "	args[3]  {invokeWfName}"
			fi
			;;
			
reruncoord)
	if [ $# = 4 ] 
	then 
		DC_CLASSPATH="${DC_HOME}/lib/module/*:$DC_HOME/lib/mapreduce/*:${DC_HOME}/lib/mapredserver/*:${DC_HOME}/conf"
		echo "start restartcoord..."
				"$JAVA" $JAVA_OPTS -cp "$DC_CLASSPATH" \
				com.paibo.datacenter.mapredserver.workflow.util.OozieReRunUtil coord $2 $3 $4
			else
				echo "Usage: dc-workflow <command> [<args>]" 
				echo "Commands:"
				echo " ./dc-workflow.sh reruncoord   Run Fetch reruncoord coordinator task"
				echo "Args: "
				echo "  args[1]  {ip}"
				echo "	args[2]  {jobId}"
				echo "	args[3]  {actionNum}"
			fi
			;;	
	
*)
    echo "Usage: $0 {init | update | submitplus |runTest|rerunwf|reruncoord}" >&2
esac





 






