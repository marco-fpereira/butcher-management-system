#! /bin/bash
sudo apt update
sudo chmod +x /tmp/setup-environment
sudo apt install -y openjdk-17-jdk
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
echo $JAVA_HOME
export PATH=$PATH:$JAVA_HOME/bin
java -version
mkdir -p ~/deploy
cd ~/deploy
sudo wget https://github.com/marco-fpereira/butcher-management-system/
cd blocking-butcher
java -jar -Dspring.profiles.active=LOCAL target/blocking-butcher-0.0.1-SNAPSHOT.jar