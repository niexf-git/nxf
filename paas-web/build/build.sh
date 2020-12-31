export JAVA_HOME=/usr/java/jdk1.7.0_71
export ANT_HOME=/usr/lib/apache-ant-1.9.4
export PATH=$JAVA_HOME/bin:$PATH:$ANT_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export RELEASE_HOME=/opt/paaswar
export ANT_OPTS="-Xms128m -Xmx256m"
echo JAVA_HOME=$JAVA_HOME
echo CLASSPATH=$CLASSPATH
echo ANT_HOME=$ANT_HOME
echo RELEASE_HOME=$RELEASE_HOME
echo ANT_OPTS=$ANT_OPTS

ant -f build.xml
cd ..
`
