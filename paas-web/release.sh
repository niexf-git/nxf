#!/bin/bash

SHELL_FOLDER=$(cd "$(dirname "$0")";pwd)


DES_FOLD=$SHELL_FOLDER/../build/

if [ ! -d "$DES_FOLD" ]
then
  mkdir -p $DES_FOLD
  echo not exist
fi

rm -rf $DES_FOLD/*

mkdir -p $DES_FOLD/paas
cp $SHELL_FOLDER/Dockerfile $DES_FOLD
cp $SHELL_FOLDER/build/opt/paaswar/paas  $DES_FOLD/ -r
