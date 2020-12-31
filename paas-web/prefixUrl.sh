#!/bin/bash
# styles 替换成 /paas/cicd/styles
# scripts 替换成 /paas/cicd/scripts
file="WebContent/index.html"
cat $file | sed 's/\=\"styles\//\=\"\/paas\/cicd\/styles\//g' | tee $file
cat $file | sed 's/\=\"scripts\//\=\"\/paas\/cicd\/scripts\//g' | tee $file