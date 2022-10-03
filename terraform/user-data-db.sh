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
mkdir -p ~/deploy
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
aws dynamodb --endpoint-url http://localhost:8000 create-table --table-name meat_tb \
	--attribute-definitions \
		AttributeName=meat_name,AttributeType=S \
	--key-schema \
		AttributeName=meat_name,KeyType=HASH \
	--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
aws dynamodb --endpoint-url http://localhost:8000 create-table --table-name purchase_tb \
	--attribute-definitions \
		AttributeName=purchase_id,AttributeType=S \
	--key-schema AttributeName=purchase_id,KeyType=HASH \
	--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
aws dynamodb --endpoint-url http://localhost:8000 create-table --table-name sale_tb \
	--attribute-definitions \
		AttributeName=sale_id,AttributeType=S \
	--key-schema AttributeName=sale_id,KeyType=HASH \
	--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5