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
tbd
and delete the following line:
tbd
<br/>
Use maven/clean package to again turn your evolved Avro schema into a class.

Step 4<br/>
Run java producer and consumer from v2 package to produce a few messages, consume them and observe the full compatibility.

**Common Exceptions**
org.apache.kafka.common.errors.TimeoutException: Failed to update metadata after 60000 ms.<br/>
This probably means your producer is not able to talk to the brokers, make sure you set the properties correctly.
<br/>
<br/>
