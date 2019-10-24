#!/bin/bash

if [ "$DEBUG_PORT"  ];then
	if [[ $DEBUG_PORT -ne 0 ]]; then 
		DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=$DEBUG_PORT,server=y,suspend=n"
	fi
fi

exec $JAVA_HOME/bin/java $DEBUG -jar $UNAM_HOME/utilitaire-nam-service.jar
exit $?
