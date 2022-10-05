#! /bin/bash
export DATABASE_URL=${aws_instance.prd-appdynamics-db[*].public_ip}
sudo apt update
sudo chmod +x /tmp/setup-environment
sudo apt install -y openjdk-17-jdk
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
echo $JAVA_HOME
export PATH=$PATH:$JAVA_HOME/bin
java -version
mkdir ~/deploy
cd ~/deploy
sudo apt install maven -y
sudo apt install git -y
git clone https://github.com/marco-fpereira/butcher-management-system.git
git checkout infra-terraform-creation
cd butcher-management-system/reactive-butcher
mvn clean install
mvn packege
java -jar -Dspring.profiles.active=PROD target/reactive-butcher-0.0.1-SNAPSHOT.jar
