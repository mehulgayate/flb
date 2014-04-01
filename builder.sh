#!/bin/bash

PROJECT_NAME=flb
TOMCAT_HOME_DIR=/evalua/servers/$PROJECT_NAME/apache-tomcat-6.0.37
TOMCAT_BIN=$TOMCAT_HOME_DIR/bin
PROJECT_HOME=`pwd`
TOMCAT_WEBAPP=$TOMCAT_HOME_DIR/webapps
TARGET_WEBAPP=$PROJECT_HOME/target/$PROJECT_NAME

echo "PROJECT HOME : $PROJECT_HOME"

cd $TOMCAT_BIN
  ./catalina.sh stop

cd $PROJECT_HOME
mvn clean

mvn compile war:exploded

if [ ! -L $TOMCAT_WEBAPP/ROOT ]; then
cd $TOMCAT_WEBAPP
ln -s $TARGET_WEBAPP ROOT
fi

cd $TARGET_WEBAPP 
rm -rf static
ln -s $PROJECT_HOME/src/main/webapp/static static

cd WEB-INF
rm -rf jsp
ln -s $PROJECT_HOME/src/main/webapp/WEB-INF/jsp jsp

cd $TOMCAT_BIN
  ./catalina.sh run


