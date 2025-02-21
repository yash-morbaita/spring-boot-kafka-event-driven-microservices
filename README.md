# spring-boot-kafka-event-driven-microservices
This repository is an demonstartes asyncronous communcation between the microservices using Kafka


Steps to Start a server:

1. Run the docker command to start a kafka server with Kraft: docker run -p 9092:9092 apache/kafka:3.9.0  or the 
docker compose file docker-compose.yml using docker compose up -d
2. Run the Order-service
3. Use postman to send requests to the order-service. It will invoke a kafka event to a topic order_topics
4. Run the Stock-service, email-service and see it pull the event.
