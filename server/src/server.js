import express from 'express';
import expressws from 'express-ws';
import cors from 'cors';
import { graphqlHTTP } from 'express-graphql';
import {db} from "./models/index.js"
import {root} from "./controllers/eolicplant.controller.js"
import {schema} from "./schema/eolicplant.schema.js"
import { __dirname } from './config/dirname.js';
import {consumer} from "./services/consumerKafka.js"

const app = express();
expressws(app);

//Allow requests from browser apps loaded in other domain
app.use(cors());

app.use('/graphql', graphqlHTTP({
  schema: schema,
  rootValue: root,
  graphiql: true,
}));

app.use(express.static(__dirname + '/../../browser'));

db.sequelize.sync();

app.ws('/progressWS', (ws, req) => {

  console.log('User connected');

  ws.on('message', (city) => {
      console.log('Starting planning of :' + city);
  });

  consumer.on("message", async function(message) {  
    let response = JSON.parse(message.value);
    if (response.progress==100) {
      try {
        const result = await db.eolicplants.update(
          { city: response.city,
            planning: response.planning },
          { where: { id: response.id } }
        )
      } catch (err) {
        console.log(err) 
      }
    }
    ws.send(message.value);
    console.log(response) 
  });
});

app.listen(3000, ()=>{
    console.log('Running server at http://localhost:3000/');
});
