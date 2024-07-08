## Commande pour démarrer le cluster
```bash
docker compose up -d
```

### Commande pour créer un TOPIC 70f=> identifiant du conteneur docker
```bash
docker exec 70f kafka-topics --create --topic demo_topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```

### Commande pour un flux interactif pour la production des messages
```bash
docker exec -it 70f kafka-console-producer --topic demo_topic --bootstrap-server localhost:9092 
```


### Commande pour lire les message à partir un flux interactif 
```bash
docker exec -it 70f kafka-console-consumer --topic demo_topic --bootstrap-server localhost:9092 
```


### Commande pour augmenter un nombre de partition
```bash
docker exec 70f kafka-topics --alter --topic critical_notifications --bootstrap-server localhost:9092 --partitions 3
```

### Commande pour soumettre une tache connector
```bash
curl -X POST -H "Content-type: application/json" --data @connector-mysql.json http://localhost:8083/connectors
```

## Commande pour démarrer un conteneur postgres
```bash
docker run --name postgres-container -e POSTGRES_PASSWORD=yourpassword -p 5432:5432 -d --network kafka-docker-composer_default postgres
```


### Commande partie registry
- Enregistrement manuel par l'api
```bash
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" --data @user.json http://localhost:8081/subjects/user-value/versions
```


### Utilisation de kafa Streams
## Exemple 1
1. Collecte des logs "TOPIC"
2. Traitement des logs "Stream"
3. Alert "TOPIC"

### Ajout d'un serveur ksqldb

```bash
docker run -d \
--name=ksqldb-server \
-v $PWD/extenstions:/etc/ksql/ext \
--network=kafka-docker-composer_default \
-e KSQL_LISTENERS=http://0.0.0.0:8088 \
-e KSQL_BOOTSTRAP_SERVERS=kafka-1:19091 \
-e KSQL_KSQL_SCHEMA_REGISTRY_URL=http://schema-registry:8081 \
-e KSQL_KSQL_LOGGING_PROCESSING_STREAM_AUTO_CREATE='true' \
-e KSQL_KSQL_LOGGING_PROCESSING_TOPIC_AUTO_CREATE='true' \
-p 8088:8088 \
confluentinc/cp-ksqldb-server:latest
```

### pour avoir un client à l'interieur du conteneur du serveur ksqldb
```bash
docker exec -it ksqldb-server ksql http://localhost:8088

```sql
CREATE STREAM users_stream (user_id INT KEY, first_name STRING,last_name STRING,email STRING) WITH (kafka_topic='users',value_format='JSON',partitions=4);
````

## Exemple 
```bash
CREATE STREAM orders (
  order_id INT,
  customer_id INT,
  product_id INT
) WITH (
  KAFKA_TOPIC='orders_topic',
  VALUE_FORMAT='JSON'
);

CREATE STREAM customers (
  customer_id INT,
  customer_name STRING
) WITH (
  KAFKA_TOPIC='customers_topic',
  VALUE_FORMAT='JSON'
);

CREATE STREAM enriched_orders AS
SELECT orders.order_id,
       orders.product_id,
       orders.customer_id,
       customers.customer_name
FROM orders
JOIN customers WITHIN 10 MINUTES
ON orders.customer_id = customers.customer_id;
```

### Fields exercice 8

temperature_readings => sensor_id, zone_id, temperature, timestamp
humidity_readings => sensor_id, zone_id, humidity, timestamp


### Correction Exercice 8

1. Création des streams

CREATE STREAM temperature_readings (
  sensor_id INT,
  zone_id INT,
  temperature DOUBLE,
  timestamp BIGINT
) WITH (
  KAFKA_TOPIC='temperature_topic',
  VALUE_FORMAT='JSON'
);

CREATE STREAM humidity_readings (
  sensor_id INT,
  zone_id INT,
  humidity DOUBLE,
  timestamp BIGINT
) WITH (
  KAFKA_TOPIC='humidity_topic',
  VALUE_FORMAT='JSON'
);

2. Insertion de données
INSERT INTO temperature_readings (sensor_id, zone_id, temperature, timestamp) VALUES (1, 101, 22.5, 1627847282000);
INSERT INTO temperature_readings (sensor_id, zone_id, temperature, timestamp) VALUES (2, 102, 23.0, 1627847342000);
INSERT INTO temperature_readings (sensor_id, zone_id, temperature, timestamp) VALUES (3, 101, 21.8, 1627847402000);

INSERT INTO humidity_readings (sensor_id, zone_id, humidity, timestamp) VALUES (1, 101, 45.0, 1627847282000);
INSERT INTO humidity_readings (sensor_id, zone_id, humidity, timestamp) VALUES (2, 102, 50.0, 1627847342000);
INSERT INTO humidity_readings (sensor_id, zone_id, humidity, timestamp) VALUES (3, 101, 48.0, 1627847402000);

3. Création de jointure
CREATE STREAM sensor_data AS
SELECT t.sensor_id,
       t.zone_id,
       t.temperature,
       h.humidity,
       t.timestamp
FROM temperature_readings t
JOIN humidity_readings h WITHIN 5 MINUTES
ON t.sensor_id = h.sensor_id
AND t.timestamp = h.timestamp;

4. Création d'une table d'aggrégation
CREATE TABLE avg_sensor_data AS
SELECT zone_id,
       AVG(temperature) AS avg_temperature,
       AVG(humidity) AS avg_humidity,
       TUMBLINGWINDOW AS window_start
FROM sensor_data
WINDOW TUMBLING (SIZE 5 MINUTES)
GROUP BY zone_id;