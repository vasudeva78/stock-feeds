#!/bin/sh
cd /home/pi/h2-database/h2-2022-06-13/h2/bin
rm *.out
java -Xms16m -Xmx16m -Xss512k -XX:+UseConcMarkSweepGC -cp h2-2.1.214.jar org.h2.tools.Server -tcp &

sleep 20s

cd /home/pi/stock-feeds/
rm *.out
java -Xms64m -Xmx64m -Xss512k -XX:+UseConcMarkSweepGC -Dmicronaut.environments=linux -jar stock-feeds.jar &