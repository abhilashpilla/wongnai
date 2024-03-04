
# To install  
mvn clean install

# To run the JAR
java -jar -Dspring.profiles.active=dev orderservice-0.0.1-SNAPSHOT.jar

# To build docker image 
docker build --build-arg JAR_FILE=target/orderservice-0.0.1-SNAPSHOT.jar -t myorg/myapp .

# To docker run: 
sudo docker run -e AWS_ACCESS_KEY_ID=??? -e AWS_SECRET_ACCESS_KEY=??? myorg/myapp


# This project uses Mongo DB change streams . Please install mongodb as a replica set. Please run the following commands:

docker run -d --rm -p 27017:27017 --name mongo1 --network mongoCluster mongo:5 mongod --replSet myReplicaSet
docker run -d --rm -p 27018:27018 --name mongo2 --network mongoCluster mongo:5 mongod --replSet myReplicaSet
docker run -d --rm -p 27019:27019 --name mongo3 --network mongoCluster mongo:5 mongod --replSet myReplicaSet

docker exec -it mongo1 mongosh --eval "rs.initiate({
 _id: \"myReplicaSet\",
 members: [
   {_id: 0, host: \"mongo1\"},
   {_id: 1, host: \"mongo2\"},
   {_id: 2, host: \"mongo3\"}
 ]
})"


# REST endpoints are below:

# create customer
POST http://localhost:8080/api/customers
{
    "name": "abhilash",
    "phone": "+971523625082",
    "latitude": "25.267410",
    "longitude": "55.292680"
}

# create driver
POST http://localhost:8080/api/drivers
{
    "name": "driver1",
    "phone": "+971523625082",
    "latitude": "25.2646231",
    "longitude": "55.3212473"
 
}

# create order
POST http://localhost:8080/api/orders
{
    "type": "FOOD",
    "latitude": "25.2691389",
    "longitude": "55.3142581",
    "customer": {
        "id": "65e4b91a68a7256d652cd5c8"
    }
}

# cancel order
PATCH http://localhost:8080/api/orders
{
    "id": "65e4ce67229f08184c4fe38a",
    "status": "CANCELLED"
}

# order accepted by restaurant
PATCH http://localhost:8080/api/orders/65e4ce67229f08184c4fe38a
{
    "id": "65e4ce67229f08184c4fe38a",
    "status": "ACCEPTED"
}

# order preparation complete by kitchen
PATCH http://localhost:8080/api/orders/65e4ce67229f08184c4fe38a
{
    "id": "65e4ce67229f08184c4fe38a",
    "status": "PREPARED"
}

# order pickedup
PATCH http://localhost:8080/api/orders/65e4ce67229f08184c4fe38a
{
    "id": "65e4ce67229f08184c4fe38a",
    "status": "DRIVING"
}


# driver driving with order
PATCH http://localhost:8080/api/drivers/65e4b2af96c61806b2e795bf
{
    "id": "65e4b2af96c61806b2e795bf",
    "status": "DRIVING",
    "latitude": "25.466433",
    "longitude": "55.4222"
}


# driver driving with order updated location
PATCH http://localhost:8080/api/drivers/65e4b2af96c61806b2e795bf
{
    "id": "65e4b2af96c61806b2e795bf",
    "status": "DRIVING",
    "latitude": "25.197197",
    "longitude": "55.2718015"
}

# order delivered
PATCH http://localhost:8080/api/orders/65e4ce67229f08184c4fe38a
{
    "id": "65e4ce67229f08184c4fe38a",
    "status": "DELIVERED"
}

# order updates using server sent events
GET http://localhost:8080/api/orders/{id}/status


# driver updates using server sent events
GET http://localhost:8080/api/drivers/{id}/status








