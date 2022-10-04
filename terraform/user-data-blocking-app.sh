#! /bin/bash
sudo apt update
sudo chmod +x /tmp/setup-environment
sudo apt install -y openjdk-17-jdk
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
echo $JAVA_HOME
export PATH=$PATH:$JAVA_HOME/bin
java -version
mkdir ~/deploy
cd ~/deploy
sudo apt install unzip -y
curl -LJO https://github.com/marco-fpereira/butcher-management-system/archive/refs/heads/main.zip
unzip butcher-management-system-main.zip
cd butcher-management-system-main/blocking-butcher
java -jar -Dspring.profiles.active=PROD executable/blocking-butcher-0.0.1-SNAPSHOT.jar