#!/bin/bash

nohup hive --service metastore >> /home/bigdata/apache-hive-2.1.1-bin/metastore.log 2>&1  &
