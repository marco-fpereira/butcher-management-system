# Butcher Management System
## MVP of a butcher management system using blocking and reactive programming to compare them


### Implemented routes
| Path | What it does |
| ------ | ------ |
| GET - /current_meat_info | Get all existing meats in table |
| GET - /current_meat_info/{meat_name} | Get a meat by its name |
| POST - /purchase_meat | Purchase a meat. If it doesn't exist, it'll be inserted |
| PUT - /sale_meat | Sale a meat |

### Purchase meat payload:
{
    "meat_name": "",
    "animal_of_origin": "",
    "purchase_price": 0.0,
    "amount_bought": 0.0
}

### Sale meat payload:
{
    "meat_name": "",
    "amount_in_kilograms": 0.0,
    "type_of_cut": ""
}

### Sources:
- Reactive flow
  - https://medium.com/@sarathtchander/building-a-reactive-crud-application-using-spring-webflux-with-aws-dynamodb-in-java-6eabdfc7813
  - https://betterprogramming.pub/aws-java-sdk-v2-dynamodb-enhanced-client-with-kotlin-spring-boot-application-f880c74193a2 
  - https://medium.com/techieconnect/reactive-dynamodb-94ee0cce0113

- Blocking flow
  - https://medium.com/@kaikeventura/api-rest-com-spring-boot-e-aws-dynamodb-5e79ecb80b62

## Docker compose to generate DynamoDB container

```sh
version: '3.8'
services:
  dynamodb-local:
    command: "-jar DynamoDBLocal.jar -sharedDb -dbPath ./data"
    image: "amazon/dynamodb-local:latest"
    container_name: dynamodb-local
    ports:
      - "8000:8000"
    volumes:
      - "./docker/dynamodb:/home/dynamodblocal/data"
    working_dir: /home/dynamodblocal
```

## DynamoDB queries to generate tables
Meat Table
```sh
aws dynamodb --endpoint-url http://localhost:8000 create-table --table-name meat_tb `
	--attribute-definitions `
		AttributeName=meat_name,AttributeType=S `
	--key-schema `
		AttributeName=meat_name,KeyType=HASH `
	--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```

Purchase Table
```sh
aws dynamodb --endpoint-url http://localhost:8000 create-table --table-name purchase_tb `
	--attribute-definitions `
		AttributeName=purchase_id,AttributeType=S `
	--key-schema AttributeName=purchase_id,KeyType=HASH `
	--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

```

Sale Table
```sh
aws dynamodb --endpoint-url http://localhost:8000 create-table --table-name sale_tb `
	--attribute-definitions `
		AttributeName=sale_id,AttributeType=S `
	--key-schema AttributeName=sale_id,KeyType=HASH `
	--provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
```

## Terraform to create infrastructure on AWS
```sh
// to do
```
