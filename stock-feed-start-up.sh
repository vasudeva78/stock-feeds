#!/bin/sh
cd /home/pi/h2-database/h2/bin
rm *.out
java -Xms16m -Xmx16m -Xss512k -XX:+UseSerialGC -cp h2-2.1.214.jar org.h2.tools.Server -tcp &

sleep 20s

cd /home/pi/stock-feeds/
rm *.out
java -Xms32m -Xmx32m -Xss512k -XX:+UseSerialGC -Dmicronaut.environments=linux -Dhttps.protocols=TLSv1.1,TLSv1.2 -jar stock-feeds.jar &