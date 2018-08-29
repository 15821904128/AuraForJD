#!/bin/bash

nohup hive --service hiveserver2 >> /home/bigdata/apache-hive-2.1.1-bin/hiveserver.log 2>&1 &
