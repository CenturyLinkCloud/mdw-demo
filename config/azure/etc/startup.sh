#!/usr/bin/env bash

# Get environment variables to show up in SSH session
eval $(printenv | sed -n "s/^\([^=]\+\)=\(.*\)$/export \1=\2/p" | sed 's/"/\\\"/g' | sed '/=/s//="/' | sed 's/$/"/' >> /etc/profile)

# starting sshd process
mkdir /run/sshd
/usr/sbin/sshd

# run the app
java -Xmx1024m -Dmdw.runtime.env=dev -Dmdw.config.location=/opt/mdw/config -jar /opt/mdw/mdw-demo.jar --spring.config.location=file:///opt/mdw/config/spring/application.yml 2>&1 > /opt/mdw/mdw.log
