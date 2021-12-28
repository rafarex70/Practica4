import { KafkaClient, Consumer } from "kafka-node";

const client = new KafkaClient({kafkaHost: "localhost:9092"});
export const consumer = new Consumer( client, [{ topic: "eoloplantCreationProgressNotifications", partition: 0 }] );

consumer.on("message", async function(message) {  console.log(message)});