# Dockerfile for deploying Azure custom image
# https://docs.microsoft.com/en-us/azure/app-service/containers/tutorial-custom-docker-image
FROM openjdk:11-jdk

RUN mkdir -p /home/logs /opt/mdw \
     && echo "root:Docker!" | chpasswd \
     && apt-get update \
     && apt-get install --yes --no-install-recommends openssh-server curl libncurses5

COPY config/azure/etc/sshd_config /etc/ssh/
COPY config/azure/etc/startup.sh /opt/mdw
RUN chmod +x /opt/mdw/startup.sh

COPY config/azure/ /opt/mdw/config
ADD build/libs/mdw-demo-2.1.6.jar /opt/mdw/mdw-demo.jar

ENV MDW_HOME /opt/mdw/git/mdw-demo/mdw/cli
ENV PATH $PATH:$MDW_HOME/bin

EXPOSE 8080 3308/tcp 2222
ENTRYPOINT ["/opt/mdw/startup.sh"]
