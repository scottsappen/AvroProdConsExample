# AvroProdConsExample
A simple Java example using a Java based consumer and producer to create Avro data stored in Kafka leveraging Schema Registry

This is a simple example of creating a Java based producer and consumer to create and read Avro records from Kafka. This demonstrates schema evolution as well:
- Utilizes the Confluent Schema Registry
- Utilizes maven

Pre-reqs:
No need to overcomplicate things, if you have a cluster great, if not just download Confluent platform from confluent.io and untar the bundle and run it on your workstation locally.
It will make it easier to connect to localhost (as these configs show) and allow things like producers to auto-create topics.

Step 1<br/>
Use maven clean/package to turn your climbinggym.avsc Avro schema into a class.

Step 2<br/>
Run java producer and consumer from v1 package to produce a few messages, consume them and observe

Step 3<br/>
Evolve the schema to include the following line:
{ "name": "squarefeet", "type": "int", "default": 0, "doc": "Square feet of the climbing gym" }
and delete the last boolean about speed climbing (see schemaevolved directory for the schema).
<br/>
Use maven/clean package to again turn your evolved Avro schema into a class.

Step 4<br/>
Run java producer and consumer from v2 package to produce a few messages, consume them and observe the full compatibility.

Tip: remember Confluent packages some command line tooling you can use as a quick and dirty consumer as well.
$ ./kafka-avro-console-consumer  --bootstrap-server localhost:9092 --topic climbinggym --from-beginning

**Common Exceptions**
org.apache.kafka.common.errors.TimeoutException: Failed to update metadata after 60000 ms.<br/>
This probably means your producer is not able to talk to the brokers, make sure you set the properties correctly.
<br/>
<br/>
org.apache.kafka.common.errors.SerializationException: Error registering Avro schema:
This probably means you don't have your schema in full compatibility mode. You could change it with something like this:
<br/>curl -X PUT -H "Content-Type: application/vnd.schemaregistry.v1+json" --data '{"compatibility": "FULL"}' http://localhost:8081/config/climbinggym-value/
