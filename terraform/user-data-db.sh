#! /bin/bash
sudo apt update
sudo apt install apt-transport-https curl gnupg-agent ca-certificates software-properties-common -y
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository -y "deb [arch=amd64] https://download.docker.com/linux/ubuntu focal stable"
sudo apt install docker-ce docker-ce-cli containerd.io -y
sudo usermod -aG docker $USER
newgrp docker
sudo systemctl start docker
sudo systemctl enable docker
docker run hello-world
mkdir ~/deploy
cd ~/deploy
echo -e \
"version: '3.8' \n\
services: \n\
  dynamodb-local: \n\
    command: "-jar DynamoDBLocal.jar -sharedDb -dbPath ./data" \n\
    image: "amazon/dynamodb-local:latest" \n\
    container_name: dynamodb-local \n\
    ports: \n\
      - "8000:8000" \n\
    volumes: \n\
      - "./docker/dynamodb:/home/dynamodblocal/data" \n\
    working_dir: /home/dynamodblocal" > docker-compose.yml
sudo apt-get install -y docker-compose-plugin
docker compose up -d
docker ps
