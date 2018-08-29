#!/bin/bash

hdfs dfs -mkdir -p /jdtest
hdfs dfs -put /home/bigdata/jdd_dataset/t_click.csv /jdtest/click
