import { KafkaClient, Producer } from "kafka-node";
const client = new KafkaClient({kafkaHost: "localhost:9092"});
const producer = new Producer(client);

export default function (planning) {
  const payload = [{ topic: "eoloplantCreationRequests", messages: JSON.stringify(planning)}];

  producer.send(payload, function(error, result) {   
    console.log("Sending payload to Kafka");    
    if (error) {      
      console.log( "Sending payload failed: ", error);    
    } else {      
      console.log("Sending payload result:", result);    
    }  
  });
}

// const payload = [{ topic: "eoloplantCreationRequests", messages: JSON.stringify(planning)}];

// setInterval( () => {  
//   producer.send(payload, function(error, result) {   
//     console.log("Sending payload to Kafka");    
//     if (error) {      
//       console.log( "Sending payload failed: ", error);    
//     } else {      
//       console.log("Sending payload result:", result);    
//     }  
//   });
// }, 10000)