#!/bin/sh

export TERM=xterm

mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n" &

while true
do
  watch -d -t -g "ls -lR . | sha1sum" && mvn compile
done