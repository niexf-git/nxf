#!/bin/bash

if [ ! $# -eq 1 ]
then
  echo USAGE:$0 filename
  exit 0
fi

file=$1
CURVERSIONFILE=VERSION.latest
day=`date +%Y%m%d`
NEWVERSIONFILE=VERSION.$day

if [ -f $CURVERSIONFILE ]
then
  rm $CURVERSIONFILE -f
fi
wget http://192.168.54.37/cmsz/paas/paas_3/Version/$CURVERSIONFILE

if [ -f $NEWVERSIONFILE ]
then
  rm $NEWVERSIONFILE -f
fi

function _paas-controller(){
   _name=$1
   NEWV=`echo ${_name##*paas-controller-}`
   NEWV=`echo ${NEWV%%.zip*}`
   OLDV=`cat $CURVERSIONFILE | grep PAAS_CONTROLLER_VERSION | awk -F "=" '{print $2}'`
   sed "s/PAAS_CONTROLLER_VERSION=${OLDV}/PAAS_CONTROLLER_VERSION=${NEWV}/" $CURVERSIONFILE >$NEWVERSIONFILE
   cp $NEWVERSIONFILE $CURVERSIONFILE
}

function _paas-monitor(){
   _name=$1
   NEWV=`echo ${_name##*paas-monitor-}`
   NEWV=`echo ${NEWV%%.zip*}`
   OLDV=`cat $CURVERSIONFILE | grep PAAS_MONITOR_VERSION | awk -F "=" '{print $2}'`
   sed "s/PAAS_MONITOR_VERSION=${OLDV}/PAAS_MONITOR_VERSION=${NEWV}/" $CURVERSIONFILE >$NEWVERSIONFILE
   cp $NEWVERSIONFILE $CURVERSIONFILE
}

function _paas_flume(){
   _name=$1
   NEWV=`echo ${_name##*paas_flume-}`
   NEWV=`echo ${NEWV%%.jar*}`
   OLDV=`cat $CURVERSIONFILE | grep FLUME_VERSION | awk -F "=" '{print $2}'`
   sed "s/FLUME_VERSION=${OLDV}/FLUME_VERSION=${NEWV}/" $CURVERSIONFILE  >$NEWVERSIONFILE
   cp $NEWVERSIONFILE $CURVERSIONFILE
}

function _paas_web(){
   _name=$1
   NEWV=`echo ${_name##*paas-}`
   NEWV=`echo ${NEWV%%.war*}`
   OLDV=`cat $CURVERSIONFILE | grep PAAS_WEB | awk -F "=" '{print $2}'`
   sed "s/PAAS_WEB=${OLDV}/PAAS_WEB=${NEWV}/" $CURVERSIONFILE >$NEWVERSIONFILE
   cp $NEWVERSIONFILE $CURVERSIONFILE
}

versionname=`echo ${file##*/}`

if [ ${versionname:0:15} == "paas-controller" ]
then
  _paas-controller $versionname
  PACKPATH=/mirrors/cmsz/paas/paas_3/paasControl
fi

if [ ${versionname:0:12} == "paas-monitor" ]
then
  _paas-monitor $versionname
  PACKPATH=/mirrors/cmsz/paas/paas_3/paasMonitor
fi

if [ ${versionname:0:10} == "paas_flume" ]
then
  _paas_flume $versionname
  PACKPATH=/mirrors/cmsz/paas/paas_3/paasLog
fi

if [ ${versionname: -3} == "war" ]
then
  _paas_web $versionname
  PACKPATH=/mirrors/cmsz/paas/paas_3/paasWeb
fi

currentDir=$(dirname $0)
sudo yum -y install expect >/dev/null

REMOATIP=192.168.54.37
VERSIONPATH=/mirrors/cmsz/paas/paas_3/Version
REMOATUSER=paas
REMOATPASSWD=Paas_0620
copyfile () {
    filename=$1
    path=$2
    paasadmPassWd="paasadm"
    expect -c "set timeout -1;
                spawn scp $currentDir/$filename $REMOATUSER@$REMOATIP:$path;
                expect {
                    *(yes/no)* {send -- yes\r;exp_continue;}
                    *assword:* {send -- $REMOATPASSWD\r;exp_continue;}
                    eof        {exit 0;}
                }";
}

copyfile $CURVERSIONFILE $VERSIONPATH
copyfile $NEWVERSIONFILE $VERSIONPATH
copyfile $file $PACKPATH
